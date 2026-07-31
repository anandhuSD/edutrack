package com.edutrack2.edutrack2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutrack2.edutrack2.model.User;
import com.edutrack2.edutrack2.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()) ||
            userRepository.existsByEmail(user.getEmail())) {
            return false; // username or email already taken
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // scramble password before saving
        userRepository.save(user);
        return true;
    }

    public User validateLogin(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()))
                .orElse(null);
    }
}