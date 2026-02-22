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
import com.petwellnessplus.dto.AppResponse;
import com.petwellnessplus.redis.RedisAuthService;
import com.petwellnessplus.security.SecurityConstants;

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

	private final JwtUtils jwtUtils;
	private final JwtProperties jwtProperties;
	private final ObjectMapper obejctMapper;
	private final RedisAuthService redisAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(jwtProperties.getHeader());

		if (authHeader == null || !authHeader.startsWith(jwtProperties.getPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(jwtProperties.getPrefix().length());

		try {
			Claims claims = jwtUtils.parseClaims(token);
			
			Long userId = claims.get(SecurityConstants.CLAIM_USER_ID, Long.class);
			String key = SecurityConstants.AUTH_USER_PREFIX + userId;
			
			String tokenJti = claims.getId();
			String redisJti = redisAuthService.getValue(key);
			
			if(redisJti == null || !tokenJti.equals(redisJti)) {
				throw new JwtException("Token revoked or replaced");
			}
			
			List<?> roles = claims.get(SecurityConstants.CLAIM_ROLES, List.class);

			List<SimpleGrantedAuthority> authorities =
			        roles.stream()
			        	 .map(Object::toString)
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

		AppResponse<String> apiResponse = AppResponse.error(status, message);
		String jsonResponse = obejctMapper.writeValueAsString(apiResponse);

		response.getWriter().write(jsonResponse);
	}

}
