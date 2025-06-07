package hr.algebra.advanced_interoperability_project.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_and_extractUsername_shouldWork() {
        String token = jwtService.generateToken("testUser");

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testUser", extractedUsername);
    }

    @Test
    void generateToken_and_extractExpiration_shouldReturnFutureDate() {
        String token = jwtService.generateToken("testUser");

        Date expiration = jwtService.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void validateToken_shouldReturnTrue_forValidToken() {
        String token = jwtService.generateToken("testUser");

        UserDetails mockUser = mock(UserDetails.class);
        when(mockUser.getUsername()).thenReturn("testUser");

        assertTrue(jwtService.validateToken(token, mockUser));
    }

    @Test
    void validateToken_shouldReturnFalse_forWrongUser() {
        String token = jwtService.generateToken("testUser");

        UserDetails mockUser = mock(UserDetails.class);
        when(mockUser.getUsername()).thenReturn("wrongUser");

        assertFalse(jwtService.validateToken(token, mockUser));
    }

    @Test
    void extractClaim_shouldReturnCorrectSubject() {
        String token = jwtService.generateToken("claimUser");

        String subject = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("claimUser", subject);
    }
}