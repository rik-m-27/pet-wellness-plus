package com.sm.petwellnessplus.services.pet;

import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Pet;
import com.sm.petwellnessplus.repositories.PetRepository;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService implements IPetService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> savePetsForAppointment(List<Pet> pets) {
        return petRepository.saveAll(pets);
    }

    @Override
    public Pet updatePet(Pet pet, Long id) {
        Pet existingPet = getPetById(id);
        existingPet.setName(pet.getName());
        existingPet.setAge(pet.getAge());
        existingPet.setColor(pet.getColor());
        existingPet.setType(pet.getType());
        existingPet.setBreed(pet.getBreed());
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.findById(id)
                .ifPresentOrElse(petRepository::delete, () -> {
                    throw new ResourceNotFoundException(FeedBackMessage.PET_NOT_FOUND);
                });
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.PET_NOT_FOUND));
    }
}
