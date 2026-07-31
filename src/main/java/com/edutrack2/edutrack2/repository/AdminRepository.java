package com.edutrack2.edutrack2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}