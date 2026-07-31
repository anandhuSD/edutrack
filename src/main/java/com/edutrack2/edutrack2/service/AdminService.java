package com.edutrack2.edutrack2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutrack2.edutrack2.model.Admin;
import com.edutrack2.edutrack2.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Creates a default admin (admin / admin123) the first time it's called, so you have a way to log in.
    public void createDefaultAdminIfMissing() {
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
        }
    }

    public Admin validateLogin(String username, String rawPassword) {
        return adminRepository.findByUsername(username)
                .filter(a -> passwordEncoder.matches(rawPassword, a.getPassword()))
                .orElse(null);
    }
}