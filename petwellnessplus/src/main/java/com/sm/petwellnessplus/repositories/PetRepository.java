package com.sm.petwellnessplus.repositories;

import com.sm.petwellnessplus.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
