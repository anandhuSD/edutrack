package com.edutrack2.edutrack2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutrack2.edutrack2.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}