package com.petwellnessplus.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.petwellnessplus.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {

	private final JwtProperties jwtProperties;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
	}
	
	public TokenBundle generateToken(CustomUserDetails userDetails) {

	    List<String> roles = userDetails.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .toList();
	    
	    String jti = UUID.randomUUID().toString();
	    long now = System.currentTimeMillis();
	    Date issuedAt = new Date(now);
	    long exp = now + jwtProperties.getExpiration().toMillis();
	    
	    String token = Jwts.builder()
		            .subject(userDetails.getUsername())
		            .claim("userId", userDetails.getId())
		            .claim("roles", roles)
		            .id(jti)
		            .issuedAt(issuedAt)
		            .expiration(new Date(exp))
		            .signWith(getSigningKey())
		            .compact();
	    TokenBundle bundle = new TokenBundle(token, jti, exp);
	    return bundle;
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
}
