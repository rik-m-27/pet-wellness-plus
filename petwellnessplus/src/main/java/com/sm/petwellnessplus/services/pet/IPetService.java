package com.sm.petwellnessplus.services.pet;

import com.sm.petwellnessplus.models.Pet;

import java.util.List;

public interface IPetService {
    List<Pet> savePetsForAppointment(List<Pet> pets);
    Pet updatePet(Pet pet, Long id);
    void deletePet(Long id);
    Pet getPetById(Long id);
}
