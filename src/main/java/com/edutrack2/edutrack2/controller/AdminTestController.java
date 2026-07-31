package com.edutrack2.edutrack2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edutrack2.edutrack2.model.Test;
import com.edutrack2.edutrack2.service.TestService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminTestController {

    @Autowired
    private TestService testService;

    // Blocks access if not logged in as admin. Returns null if OK to proceed.
    private String blockIfNotAdmin(HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/admin/login";
        }
        return null;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        return "admin-dashboard";
    }

    // Show list of tests + button to add a new one
    @GetMapping("/tests")
    public String listTests(HttpSession session, Model model) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        model.addAttribute("tests", testService.getAllTests());
        return "admin-tests";
    }

    @GetMapping("/tests/new")
    public String newTestForm(HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        return "admin-test-new";
    }

    @PostMapping("/tests/new")
    public String createTest(@RequestParam String testName, HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        Test test = testService.createTest(testName);
        return "redirect:/admin/tests/" + test.getId() + "/questions";
    }

    // Show the "add a question" form for a specific test, plus questions added so far
    @GetMapping("/tests/{id}/questions")
    public String addQuestionForm(@PathVariable Long id, HttpSession session, Model model) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        Test test = testService.getTestById(id);
        model.addAttribute("test", test);
        return "admin-add-question";
    }

    @PostMapping("/tests/{id}/questions")
    public String saveQuestion(@PathVariable Long id,
                                @RequestParam String questionText,
                                @RequestParam String optionA,
                                @RequestParam String optionB,
                                @RequestParam String optionC,
                                @RequestParam String optionD,
                                @RequestParam String correctOption,
                                HttpSession session) {
        String block = blockIfNotAdmin(session);
        if (block != null) return block;
        testService.addQuestion(id, questionText, optionA, optionB, optionC, optionD, correctOption);
        return "redirect:/admin/tests/" + id + "/questions";
    }
}