package com.edutrack2.edutrack2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edutrack2.edutrack2.model.Test;
import com.edutrack2.edutrack2.service.TestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ExamController {

    @Autowired
    private TestService testService;

    private String blockIfNotUser(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        return null;
    }

    @GetMapping("/exam/{id}")
    public String takeExam(@PathVariable Long id, HttpSession session, Model model) {
        String block = blockIfNotUser(session);
        if (block != null) return block;
        Test test = testService.getTestById(id);
        model.addAttribute("test", test);
        return "exam";
    }

    @PostMapping("/exam/{id}/submit")
    public String submitExam(@PathVariable Long id, HttpServletRequest request,
                              HttpSession session, Model model) {
        String block = blockIfNotUser(session);
        if (block != null) return block;

        Test test = testService.getTestById(id);

        // Every form field named "q_<questionId>" holds the option the user picked
        Map<Long, String> answers = new HashMap<>();
        for (var question : test.getQuestions()) {
            String picked = request.getParameter("q_" + question.getId());
            if (picked != null) {
                answers.put(question.getId(), picked);
            }
        }

        int score = testService.calculateScore(test, answers);
        model.addAttribute("test", test);
        model.addAttribute("score", score);
        model.addAttribute("total", test.getQuestions().size());
        return "exam-result";
    }
}