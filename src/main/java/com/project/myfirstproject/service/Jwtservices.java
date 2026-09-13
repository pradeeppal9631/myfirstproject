//package com.project.myfirstproject.service;
//
//import io.jsonwebtoken.Jwts;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//public class Jwtservices {
//    private final String SECRET_KEY =
//            "mySecretKeyForJwtLearningProject123456789";
//
//    private final SecretKey key =
//            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//
//    public String generateToken(String username) {
//
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(
//                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
//                )
//                .signWith(key)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//
//        return Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//
//        try {
//            Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token);
//
//            return true;
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//}
