package com.petwellnessplus.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenBundle {
	private final String token;
	private final String jti;
	private final long exp;
}
