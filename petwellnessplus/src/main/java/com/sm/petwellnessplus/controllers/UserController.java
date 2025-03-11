package com.sm.petwellnessplus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public void add(@RequestBody User user) {
		userService.add(user);
	}
	
}
