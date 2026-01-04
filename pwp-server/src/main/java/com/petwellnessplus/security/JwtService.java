package com.petwellnessplus.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.petwellnessplus.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

	private final JwtProperties jwtProperties;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getToken().getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(CustomUserDetails userDetails) {

	    List<String> roles = userDetails.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .toList();

	    return Jwts.builder()
	            .subject(userDetails.getUsername())
	            .claim("userId", userDetails.getId())
	            .claim("roles", roles)
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
	            .signWith(getSigningKey())
	            .compact();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
}
