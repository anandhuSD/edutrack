package com.edutrack2.edutrack2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByOrderByCreatedAtDesc();
}