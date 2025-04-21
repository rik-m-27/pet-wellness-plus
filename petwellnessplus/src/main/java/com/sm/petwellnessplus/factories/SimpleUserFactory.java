package com.sm.petwellnessplus.factories;

import org.springframework.stereotype.Component;

import com.sm.petwellnessplus.exceptions.UserAlreadyExistsException;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.UserRepository;
import com.sm.petwellnessplus.requests.RegistrationRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {

	private final UserRepository userRepository;
	private final VeterinarianFactory veterinarianFactory;
	private final PatientFactory patientFactory;
	private final AdminFactory adminFactory;

	@Override
	public User createUser(RegistrationRequest registrationRequest) {
		if (userRepository.existsByEmail(registrationRequest.getEmail())) {
			throw new UserAlreadyExistsException("Oops! " + registrationRequest.getEmail() + " already exists!");
		}

		System.out.println("\n\n\n" + registrationRequest.toString() + "\n\n\n");

		switch (registrationRequest.getUserType()) {
			case "VETERINARIAN":
				return veterinarianFactory.createVeterinarian(registrationRequest);
			case "PATIENT":
				return patientFactory.createPatient(registrationRequest);
			case "ADMIN":
				return adminFactory.createAdmin(registrationRequest);
			default:
				return null;
		}
	}
}
