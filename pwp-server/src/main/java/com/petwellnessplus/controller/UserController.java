package com.petwellnessplus.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
	
	@GetMapping("/test")
	public String testEndpoint() {
		return "User access successful!";
	}
	
}