package com.sm.petwellnessplus.factories;

import org.springframework.stereotype.Service;

import com.sm.petwellnessplus.models.Veterinarian;
import com.sm.petwellnessplus.repositories.VeterinarianRepository;
import com.sm.petwellnessplus.requests.RegistrationRequest;
import com.sm.petwellnessplus.services.user.UserAttributesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {

    private final VeterinarianRepository veterinarianRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Veterinarian createVeterinarian(RegistrationRequest request) {

        Veterinarian veterinarian = new Veterinarian();
        userAttributesMapper.setCommonAttributes(request, veterinarian);
        veterinarian.setSpecialization(request.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }

}
