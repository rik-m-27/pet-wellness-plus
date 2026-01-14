package com.petwellnessplus.security.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private final String secret;
	private final Duration expiration;
	private final String header;
	private final String prefix;
}
