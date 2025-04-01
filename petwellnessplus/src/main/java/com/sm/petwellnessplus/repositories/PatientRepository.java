package com.sm.petwellnessplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
