package com.project.myfirstproject.jwtlearn;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class Jwtlearning {

    private final String SECRET_KEY =
            "mySecretKeyForJwtLearningProject123456789";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // JWT token generate karne ka method
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(key)
                .compact();
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }


    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

}