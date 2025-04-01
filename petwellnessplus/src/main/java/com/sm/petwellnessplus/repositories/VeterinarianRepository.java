package com.sm.petwellnessplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
