package com.sm.petwellnessplus.factory;

import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.request.RegistrationRequest;

public interface UserFactory {
	public User createUser(RegistrationRequest registrationRequest);
}
