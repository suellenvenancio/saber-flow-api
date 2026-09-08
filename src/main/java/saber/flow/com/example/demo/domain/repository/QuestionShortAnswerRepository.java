package saber.flow.com.example.demo.domain.repository;

import java.util.List;

import saber.flow.com.example.demo.domain.model.QuestionShortAnswerKey;

public interface QuestionShortAnswerRepository {
    List<QuestionShortAnswerKey> findByQuestionId(String questionId);
}