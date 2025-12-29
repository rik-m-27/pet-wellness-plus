package com.sm.petwellnessplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.Veterinarian;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
