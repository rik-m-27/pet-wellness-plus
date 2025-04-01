package com.sm.petwellnessplus.factories;

import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.models.Admin;
import com.sm.petwellnessplus.repositories.AdminRepository;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.services.user.UserAttributesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminFactory {

    private final AdminRepository adminRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Admin createAdmin(RegistrationRequest registrationRequest) {
        Admin admin = new Admin();
        userAttributesMapper.setCommonAttributes(registrationRequest, admin);
        return adminRepository.save(admin);
    }

}
