package com.rama.backend_spring_boot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @Mock
    private String secret;

    @InjectMocks
    private JwtUtil jwtUtil;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_TOKEN = "dummy.token.here";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secret = "thisisaverysecurejwtsecretkey";  // Set a mock secret key
    }

    @Test
    void testGenerateToken() {
        // Arrange
        String username = TEST_USERNAME;

        // Act
        String token = jwtUtil.generateToken(username);

        // Assert
        assertNotNull(token);  // Ensure token is generated
        assertTrue(token.startsWith("eyJ"));  // Ensure token has JWT structure
    }

    @Test
    void testExtractUsername() {
        // Arrange
        String username = TEST_USERNAME;
        String token = jwtUtil.generateToken(username);

        // Act
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername);  // Ensure the username is correctly extracted
    }

    @Test
    void testValidateTokenValid() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME);

        // Act
        boolean isValid = jwtUtil.validateToken(token, TEST_USERNAME);

        // Assert
        assertTrue(isValid);  // Ensure the token is valid for the correct username
    }

    @Test
    void testValidateTokenInvalid() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME);

        // Act
        boolean isValid = jwtUtil.validateToken(token, "wrongUsername");

        // Assert
        assertFalse(isValid);  // Ensure the token is not valid for the wrong username
    }

    @Test
    void testIsTokenExpired() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_USERNAME);

        // Act
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Assert
        assertFalse(isExpired);  // Ensure the token is not expired immediately after creation

        // Simulate token expiration by setting the expiration date to past
        long pastMillis = System.currentTimeMillis() - 10000;
        SecretKey mockSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
        Claims mockClaims = mock(Claims.class);
        when(mockClaims.getExpiration()).thenReturn(new Date(pastMillis));
        when(Jwts.parserBuilder().setSigningKey(mockSecretKey).build().parseClaimsJws(token).getBody()).thenReturn(mockClaims);

        // Act again
        isExpired = jwtUtil.isTokenExpired(token);

        // Assert
        assertTrue(isExpired);  // Ensure the token is expired after simulating expiration
    }
}
