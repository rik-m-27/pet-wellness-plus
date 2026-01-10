package com.petwellnessplus.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petwellnessplus.dto.ApiResponse;
import com.petwellnessplus.redis.RedisAuthService;
import com.petwellnessplus.redis.RedisKeys;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final ObjectMapper obejctMapper;
	private final RedisAuthService redisAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		try {
			Claims claims = jwtService.parseClaims(token);
			
			Long userId = claims.get("userId", Long.class);
			String key = RedisKeys.AUTH_USER_PREFIX + userId;
			
			String tokenJti = claims.getId();
			String redisJti = redisAuthService.getValue(key);
			
			if(redisJti == null || !tokenJti.equals(redisJti)) {
				throw new JwtException("Token revoked or replaced");
			}
			
			List<?> roles = claims.get("roles", List.class);

			List<SimpleGrantedAuthority> authorities =
			        roles.stream()
			        	 .map(String.class::cast)
			             .map(SimpleGrantedAuthority::new)
			             .toList();

			
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, authorities));

			System.out.println(
					"SecurityContext Authentication: " + SecurityContextHolder.getContext().getAuthentication());

			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException e) {
			handleException(response, "Token has expired", HttpStatus.UNAUTHORIZED);
		} catch (JwtException e) {
			handleException(response, e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			handleException(response, "Authentication failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private void handleException(HttpServletResponse response, String message, HttpStatus status) throws IOException {

		response.setStatus(status.value());
		response.setContentType("application/json");

		ApiResponse<String> apiResponse = ApiResponse.error(status, message);
		String jsonResponse = obejctMapper.writeValueAsString(apiResponse);

		response.getWriter().write(jsonResponse);
	}

}
