package com.edutrack2.edutrack2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edutrack2.edutrack2.service.NotificationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    @Autowired
    private NotificationService notificationService;

    private String blockIfNotAdmin(HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) return "redirect:/admin/login";
        return null;
    }

    @GetMapping
    public String page(HttpSession session, Model model) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        model.addAttribute("notifications", notificationService.getAll());
        return "admin-notifications";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String message, HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        notificationService.addNotification(title, message);
        return "redirect:/admin/notifications";
    }
}