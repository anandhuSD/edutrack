package com.edutrack2.edutrack2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}