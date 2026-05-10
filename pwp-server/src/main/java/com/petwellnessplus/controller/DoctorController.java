package com.petwellnessplus.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('DOCTOR')")
public class DoctorController {
	
	@GetMapping("/test")
	public String testEndpoint() {
		return "Doctor access successful!";
	}
	
}