package com.petwellnessplus.security.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String token;
	private Duration expiration;
}
