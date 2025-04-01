package com.sm.petwellnessplus.factories;

import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.requests.RegistrationRequest;

public interface UserFactory {
	public User createUser(RegistrationRequest registrationRequest);
}
