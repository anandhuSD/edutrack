package com.edutrack2.edutrack2.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutrack2.edutrack2.model.Question;
import com.edutrack2.edutrack2.model.Test;
import com.edutrack2.edutrack2.repository.QuestionRepository;
import com.edutrack2.edutrack2.repository.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Test createTest(String testName) {
        Test test = new Test();
        test.setTestName(testName);
        return testRepository.save(test);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public void addQuestion(Long testId, String questionText, String optionA,
                             String optionB, String optionC, String optionD, String correctOption) {
        Test test = getTestById(testId);
        Question q = new Question();
        q.setQuestionText(questionText);
        q.setOptionA(optionA);
        q.setOptionB(optionB);
        q.setOptionC(optionC);
        q.setOptionD(optionD);
        q.setCorrectOption(correctOption);
        q.setTest(test);
        questionRepository.save(q);
    }

    // answers: map of questionId -> the option the user picked ("A"/"B"/"C"/"D")
    public int calculateScore(Test test, Map<Long, String> answers) {
        int score = 0;
        for (Question q : test.getQuestions()) {
            String submitted = answers.get(q.getId());
            if (submitted != null && submitted.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }
        return score;
    }
}