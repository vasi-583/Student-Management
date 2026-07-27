package com.vasi.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	 private static final String SECRET = "vasi-student-management-secret-key-2026-change-this";
	 private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	 private final long EXPIRATIONS_MS = 1000 * 60 *60 * 10; //10 Hrs
	
	public String generateTokens(String username , String role) {
		return Jwts.builder()
				.subject(username)
				.claim("role" ,role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+EXPIRATIONS_MS))
				.signWith(key)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parser().verifyWith(key).build()
				.parseSignedClaims(token).getPayload().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
}
