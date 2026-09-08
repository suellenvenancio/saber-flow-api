package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.UserAnswer;

public interface UserAnswerRepository {
    UserAnswer save(UserAnswer userAnswer);
    Optional<UserAnswer> findById(String id);
    List<UserAnswer> findAll();
    List<UserAnswer> findByUserId(String userId);
    List<UserAnswer> findByQuestionId(String questionId);
    void deleteById(String id);
}
