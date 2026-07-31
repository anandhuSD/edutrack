package com.edutrack2.edutrack2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutrack2.edutrack2.model.Activity;
import com.edutrack2.edutrack2.repository.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void addActivity(String title, String description) {
        Activity a = new Activity();
        a.setTitle(title);
        a.setDescription(description);
        activityRepository.save(a);
    }

    public List<Activity> getAll() {
        return activityRepository.findAllByOrderByCreatedAtDesc();
    }
}