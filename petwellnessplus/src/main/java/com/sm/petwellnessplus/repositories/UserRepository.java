package com.sm.petwellnessplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	
}
