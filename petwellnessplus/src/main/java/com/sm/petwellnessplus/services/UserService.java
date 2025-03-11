package com.sm.petwellnessplus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void add(User user) {
		userRepository.save(user);
	}
	
}
