package hr.algebra.advanced_interoperability_project.service;

import hr.algebra.advanced_interoperability_project.domain.RefreshToken;
import hr.algebra.advanced_interoperability_project.domain.UserInfo;
import hr.algebra.advanced_interoperability_project.repository.RefreshTokenRepository;
import hr.algebra.advanced_interoperability_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        userInfo = new UserInfo();
        userInfo.setUsername("testUser");
    }

    @Test
    void createRefreshToken_shouldReturnSavedToken() {
        when(userRepository.findByUsername("testUser")).thenReturn(userInfo);
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken("testUser");

        assertNotNull(result.getToken());
        assertEquals(userInfo, result.getUserInfo());
        assertTrue(result.getExpiryDate().isAfter(Instant.now()));
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void findByToken_shouldReturnToken() {
        RefreshToken token = RefreshToken.builder()
                .token("abc123")
                .userInfo(userInfo)
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        when(refreshTokenRepository.findByToken("abc123")).thenReturn(Optional.of(token));
        Optional<RefreshToken> result = refreshTokenService.findByToken("abc123");
        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getToken());
    }

    @Test
    void verifyExpiration_shouldReturnToken() {
        RefreshToken token = RefreshToken.builder()
                .token("abc123")
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        RefreshToken result = refreshTokenService.verifyExpiration(token);
        assertEquals(token, result);
    }

    @Test
    void verifyExpiration_shouldThrowException() {
        RefreshToken token = RefreshToken.builder()
                .token("expiredToken")
                .expiryDate(Instant.now().minusMillis(1000))
                .build();
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                refreshTokenService.verifyExpiration(token));
        assertTrue(exception.getMessage().contains("Refresh token is expired"));
        verify(refreshTokenRepository).delete(token);
    }
}