package com.vasi.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	@Value("${jwt.secret:vasi-student-management-secret-key-2026-change this}")
	private String secreString;
	
	private SecretKey key;
	@PostConstruct
	public void init() {
		String secretString = null;
		key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
	}
	
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

	public String extractRole(String token) {
		 return Jwts.parser().verifyWith(key).build()
		            .parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	
}
