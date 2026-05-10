package com.petwellnessplus.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class DoctorService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(rollbackFor = Exception.class)
	public User signupDoctor(SignupRequest request) {

	    User user = new User();
	    user.setUsername(request.getUsername());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setEnabled(true);

	    User savedUser = userRepository.save(user);

	    Role userRole = roleRepository.findByName("DOCTOR")
	            .orElseThrow(() -> new IllegalStateException("ROLE_DOCTOR not found"));

	    UserRole userRoleMapping = new UserRole();
	    userRoleMapping.setUser(savedUser);
	    userRoleMapping.setRole(userRole);
	    userRoleMapping.setStatus(RoleStatus.PENDING);

	    savedUser.getUserRoles().add(userRoleMapping);

	    User theSavedser = userRepository.save(savedUser);
	    
	    // here we have to configure email part
	    // which will send api end point like - POST /doctor/verification with token or otp based.
	    // there it will be able to put documents,
	    
	    return theSavedser;
	}
	
}
