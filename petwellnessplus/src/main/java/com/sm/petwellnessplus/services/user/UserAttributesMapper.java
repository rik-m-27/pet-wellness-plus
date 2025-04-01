package com.sm.petwellnessplus.services.user;

import org.springframework.stereotype.Component;

import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.requests.RegistrationRequest;

@Component
public class UserAttributesMapper {

    public void setCommonAttributes(RegistrationRequest source, User target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setGender(source.getGender());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEnable(source.isEnable());
        target.setPassword(source.getPassword());
        target.setUserType(source.getUserType());
    }

}
