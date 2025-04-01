package com.sm.petwellnessplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.petwellnessplus.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
