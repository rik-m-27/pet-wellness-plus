package com.sm.petwellnessplus.factories;

import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.models.Patient;
import com.sm.petwellnessplus.repositories.PatientRepository;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.services.user.UserAttributesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientFactory {

    private final PatientRepository patientRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Patient createPatient(RegistrationRequest registrationRequest) {
        Patient patient = new Patient();
        userAttributesMapper.setCommonAttributes(registrationRequest, patient);
        return patientRepository.save(patient);
    }

}
