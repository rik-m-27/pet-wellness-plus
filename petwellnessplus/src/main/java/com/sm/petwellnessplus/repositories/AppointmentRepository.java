package com.sm.petwellnessplus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.Appointment;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNo(String appointmentNo);
}
