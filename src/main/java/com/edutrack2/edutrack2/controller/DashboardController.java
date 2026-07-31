package com.edutrack2.edutrack2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edutrack2.edutrack2.service.ActivityService;
import com.edutrack2.edutrack2.service.NotificationService;
import com.edutrack2.edutrack2.service.TestService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private TestService testService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ActivityService activityService;

    private String requireLogin(HttpSession session, Model model) {
        String user = (String) session.getAttribute("loggedInUser");
        if (user == null) return null;
        model.addAttribute("username", user);
        return user;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (requireLogin(session, model) == null) return "redirect:/login";
        return "dashboard";
    }

    @GetMapping("/test-series")
    public String testSeries(HttpSession session, Model model) {
        if (requireLogin(session, model) == null) return "redirect:/login";
        model.addAttribute("tests", testService.getAllTests());
        return "test-series";
    }

    @GetMapping("/activities")
    public String activities(HttpSession session, Model model) {
        if (requireLogin(session, model) == null) return "redirect:/login";
        model.addAttribute("activities", activityService.getAll());
        return "activities";
    }

    @GetMapping("/notifications")
    public String notifications(HttpSession session, Model model) {
        if (requireLogin(session, model) == null) return "redirect:/login";
        model.addAttribute("notifications", notificationService.getAll());
        return "notifications";
    }

    @GetMapping("/performance")
    public String performance(HttpSession session, Model model) {
        if (requireLogin(session, model) == null) return "redirect:/login";
        return "performance";
    }
}