package com.petwellnessplus.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petwellnessplus.dto.SignupRequest;
import com.petwellnessplus.model.Role;
import com.petwellnessplus.model.User;
import com.petwellnessplus.repository.RoleRepository;
import com.petwellnessplus.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, 
						RoleRepository roleRepository,
						PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User signupUser(SignupRequest request) {
		
		// assuming validated for now.
		
		User user = new User();
		user.setUsername(request.getUsername());
		Optional<Role> role = roleRepository.findByName("USER");
		user.setRoles(Set.of(role.get()));
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		return userRepository.save(user);
	}
}
