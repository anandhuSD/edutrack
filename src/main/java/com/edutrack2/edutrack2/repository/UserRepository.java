package com.edutrack2.edutrack2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
