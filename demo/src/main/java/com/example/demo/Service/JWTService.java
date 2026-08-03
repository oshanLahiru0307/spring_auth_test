package com.example.demo.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    private static final String secretKey = "adxahhwhjwjdhsadxahhwhjwjdhsmine";

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String GenerateToken(Map<String, Object> claim, String email){
        return Jwts.builder()
                .claims(claim)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+360000))
                .signWith(getSecretKey())
                .compact();
    }

    public String ExtractUserEmail(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String email){
        String userEmail = ExtractUserEmail(token);
        return userEmail.equals(email);
    }
}
