package com.edutrack2.edutrack2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edutrack2.edutrack2.model.Admin;
import com.edutrack2.edutrack2.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    // Visit this ONCE in your browser to create the default admin account.
    // Username: admin   Password: admin123
    @GetMapping("/setup")
    @ResponseBody
    public String setup() {
        adminService.createDefaultAdminIfMissing();
        return "Default admin ready. Username: admin / Password: admin123. You can now go to /admin/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                         @RequestParam String password,
                         HttpSession session,
                         Model model) {
        Admin admin = adminService.validateLogin(username, password);
        if (admin != null) {
            session.setAttribute("loggedInAdmin", admin.getUsername());
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInAdmin");
        return "redirect:/admin/login";
    }
}