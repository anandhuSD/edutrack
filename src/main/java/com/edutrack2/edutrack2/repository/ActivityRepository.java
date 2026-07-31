package com.edutrack2.edutrack2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByOrderByCreatedAtDesc();
}