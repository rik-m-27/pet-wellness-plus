package com.petwellnessplus.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.petwellnessplus.config.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private final JwtProperties jwtProperties;
	
	public JwtService(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}
	  
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getToken().getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+jwtProperties.getExpiration()))
				.signWith(getSigningKey())
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return userDetails.getUsername().equals(username);
	}
}
