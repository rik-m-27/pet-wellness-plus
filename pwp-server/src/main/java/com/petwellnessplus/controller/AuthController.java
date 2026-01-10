package com.petwellnessplus.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petwellnessplus.dto.ApiResponse;
import com.petwellnessplus.dto.LoginRequest;
import com.petwellnessplus.dto.SignupRequest;
import com.petwellnessplus.model.User;
import com.petwellnessplus.redis.RedisAuthService;
import com.petwellnessplus.redis.RedisKeys;
import com.petwellnessplus.security.CustomUserDetails;
import com.petwellnessplus.security.jwt.JwtService;
import com.petwellnessplus.security.jwt.TokenBundle;
import com.petwellnessplus.service.DoctorService;
import com.petwellnessplus.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	private final DoctorService doctorService;
	private final RedisAuthService redisAuthService;

	@PostMapping("/logout")
	public ResponseEntity<String> logout(Authentication authentication){
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
	    }

	    Long userId = (Long)authentication.getPrincipal();
	    String key = RedisKeys.AUTH_USER_PREFIX + userId;
	    
	    redisAuthService.delete(key);

	    return ResponseEntity.ok("Logged out successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {

		try {
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			TokenBundle tokenBundle = jwtService.generateToken(userDetails);
			
			long ttlMillis = tokenBundle.getExp() - System.currentTimeMillis();
			
			if(ttlMillis <= 0) {
				throw new IllegalStateException("Token generation failed");
			}
			
			String key = RedisKeys.AUTH_USER_PREFIX + userDetails.getId();
			
			redisAuthService.store(key, tokenBundle.getJti(), Duration.ofMillis(ttlMillis));
			
			return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, tokenBundle.getToken()));
			
		} catch (InternalAuthenticationServiceException ex) {
		    Throwable cause = ex.getCause();
		    if (cause instanceof InsufficientAuthenticationException) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN)
		                .body(ApiResponse.error(HttpStatus.FORBIDDEN, cause.getMessage()));
		    }
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));

		} catch (BadCredentialsException | UsernameNotFoundException ex) {
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		            .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
		}
	}

	@PostMapping("/signup/user")
	public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

		User user = userService.signupUser(signupRequest);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("signup failed!!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("signup successfully!!");

	}
	
	@PostMapping("/signup/doctor")
	public ResponseEntity<String> signupDoctor(@RequestBody SignupRequest signupRequest){
		
		User doctor = doctorService.signupDoctor(signupRequest);
		if (doctor == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("signup failed!!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("signup successfully!!");
	}
	
}
