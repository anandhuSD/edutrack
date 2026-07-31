package com.edutrack2.edutrack2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutrack2.edutrack2.model.Notification;
import com.edutrack2.edutrack2.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void addNotification(String title, String message) {
        Notification n = new Notification();
        n.setTitle(title);
        n.setMessage(message);
        notificationRepository.save(n);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }
}