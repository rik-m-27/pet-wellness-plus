package com.petwellnessplus.security;

import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petwellnessplus.enums.RoleStatus;
import com.petwellnessplus.model.User;
import com.petwellnessplus.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsernameWithUserRoles(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if(!user.isEnabled()) {
        	throw new DisabledException("User is not enabled.");
        }
        
        List<SimpleGrantedAuthority> authorities =
                user.getUserRoles().stream()
                        .filter(ur -> ur.getStatus() == RoleStatus.ACTIVE)
                        .map(ur -> new SimpleGrantedAuthority(ur.getRole().getName()))
                        .toList();

        
        if (authorities.isEmpty()) {
            throw new InsufficientAuthenticationException(
            		"User has no approved roles"
            );
        }
        
        return new CustomUserDetails(user, authorities);
    }
}
