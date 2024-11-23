package com.rama.backend_spring_boot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;  // Secret from application.properties or application.yml
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours in milliseconds

    // Create a SecretKey object, either from the injected secret string or using Keys.secretKeyFor
    private SecretKey getSecretKey() {
        if (secret.length() >= 32) {
            // If the injected secret is sufficiently long (>= 256 bits), use it directly
            return Keys.hmacShaKeyFor(secret.getBytes());
        } else {
            // If the injected secret is too short, generate a secure key for HS256
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())  // Use the SecretKey here
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())  // Use the SecretKey here
                .compact();
    }
}
