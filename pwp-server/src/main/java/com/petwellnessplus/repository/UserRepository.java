package com.petwellnessplus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petwellnessplus.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByUsername(String username);
    
    @Query("""
            select u from User u
            left join fetch u.userRoles ur
            left join fetch ur.role
            where u.username = :username
        """)
        Optional<User> findByUsernameWithUserRoles(@Param("username") String username);

}
