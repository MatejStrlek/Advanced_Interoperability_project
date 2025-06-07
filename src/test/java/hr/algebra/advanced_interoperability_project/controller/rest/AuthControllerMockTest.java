package hr.algebra.advanced_interoperability_project.controller.rest;

import hr.algebra.advanced_interoperability_project.controller.AuthController;
import hr.algebra.advanced_interoperability_project.domain.RefreshToken;
import hr.algebra.advanced_interoperability_project.domain.UserInfo;
import hr.algebra.advanced_interoperability_project.dto.AuthRequestDTO;
import hr.algebra.advanced_interoperability_project.dto.JwtResponseDTO;
import hr.algebra.advanced_interoperability_project.dto.RefreshTokenRequestDTO;
import hr.algebra.advanced_interoperability_project.service.JwtService;
import hr.algebra.advanced_interoperability_project.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerMockTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthController authController;

    private AuthRequestDTO authRequestDTO;
    private RefreshTokenRequestDTO refreshTokenRequestDTO;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        authRequestDTO = new AuthRequestDTO("testUser", "testPassword");
        refreshTokenRequestDTO = new RefreshTokenRequestDTO("sample-refresh-token");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testUser");

        refreshToken = new RefreshToken();
        refreshToken.setToken("sample-refresh-token");
        refreshToken.setUserInfo(userInfo);
    }

    @Test
    void authenticateAndGetToken_success() {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());

        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(authInputToken)).thenReturn(mockAuthentication);
        when(jwtService.generateToken("testUser")).thenReturn("access-token");
        when(refreshTokenService.createRefreshToken("testUser")).thenReturn(refreshToken);

        JwtResponseDTO response = authController.authenticateAndGetToken(authRequestDTO);

        assertEquals("access-token", response.getAccessToken());
        assertEquals("sample-refresh-token", response.getToken());
    }

    @Test
    void authenticateAndGetToken_failure() {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());

        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(authInputToken)).thenReturn(mockAuthentication);

        assertThrows(UsernameNotFoundException.class, () ->
                authController.authenticateAndGetToken(authRequestDTO));
    }

    @Test
    void refreshToken_success() {
        when(refreshTokenService.findByToken("sample-refresh-token"))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken))
                .thenReturn(refreshToken);
        when(jwtService.generateToken("testUser"))
                .thenReturn("new-access-token");

        JwtResponseDTO response = authController.refreshToken(refreshTokenRequestDTO);

        assertEquals("new-access-token", response.getAccessToken());
        assertEquals("sample-refresh-token", response.getToken());
    }

    @Test
    void refreshToken_tokenNotFound() {
        when(refreshTokenService.findByToken("sample-refresh-token"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authController.refreshToken(refreshTokenRequestDTO));

        assertEquals("Refresh Token is not in DB..!!", exception.getMessage());
    }

    @Test
    void logout_doesNotThrowException() {
        assertDoesNotThrow(() -> authController.logout());
    }
}