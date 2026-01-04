package com.petwellnessplus.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petwellnessplus.dto.SignupRequest;
import com.petwellnessplus.enums.RoleStatus;
import com.petwellnessplus.model.Role;
import com.petwellnessplus.model.User;
import com.petwellnessplus.model.UserRole;
import com.petwellnessplus.repository.RoleRepository;
import com.petwellnessplus.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User signupUser(SignupRequest request) {

	    User user = new User();
	    user.setUsername(request.getUsername());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setEnabled(true);

	    User savedUser = userRepository.save(user);

	    Role userRole = roleRepository.findByName("USER")
	            .orElseThrow(() -> new IllegalStateException("ROLE_USER not found"));

	    UserRole userRoleMapping = new UserRole();
	    userRoleMapping.setUser(savedUser);
	    userRoleMapping.setRole(userRole);
	    userRoleMapping.setStatus(RoleStatus.ACTIVE);

	    savedUser.getUserRoles().add(userRoleMapping);

	    return userRepository.save(savedUser);
	}

}
