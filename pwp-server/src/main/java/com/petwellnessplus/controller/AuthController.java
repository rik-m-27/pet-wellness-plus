package com.petwellnessplus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petwellnessplus.dto.LoginRequest;
import com.petwellnessplus.dto.SignupRequest;
import com.petwellnessplus.model.User;
import com.petwellnessplus.security.CustomUserDetails;
import com.petwellnessplus.security.JwtService;
import com.petwellnessplus.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String jwt = jwtService.generateToken(userDetails);
		return ResponseEntity.ok(jwt);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

		User user = userService.signupUser(signupRequest);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("signup failed!!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("signup successfully!!");

	}
}
