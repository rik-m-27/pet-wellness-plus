package com.petwellnessplus.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petwellnessplus.dto.ApiResponse;
import com.petwellnessplus.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final ObjectMapper obejctMapper;
	
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
									ObjectMapper objectMapper) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.obejctMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);
		
		try {
			Claims claims = jwtService.parseClaims(token);
			String username = claims.getSubject();

			UserDetails user = userDetailsService.loadUserByUsername(username);

			SecurityContextHolder.getContext().setAuthentication(
			    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
			);
			
			System.out.println("SecurityContext Authentication: " + SecurityContextHolder.getContext().getAuthentication());
			
			filterChain.doFilter(request, response);
			
		} catch (ExpiredJwtException e) {
	        handleException(response, "Token has expired", HttpStatus.UNAUTHORIZED);
	    } catch (JwtException e) {
	        handleException(response, "Invalid token format or signature", HttpStatus.UNAUTHORIZED);
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
