package com.edutrack2.edutrack2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edutrack2.edutrack2.service.ActivityService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/activities")
public class AdminActivityController {

    @Autowired
    private ActivityService activityService;

    private String blockIfNotAdmin(HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/admin/login";
        return null;
    }

    @GetMapping
    public String page(HttpSession session, Model model) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        model.addAttribute("activities", activityService.getAll());
        return "admin-activities";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String description, HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        activityService.addActivity(title, description);
        return "redirect:/admin/activities";
    }
}