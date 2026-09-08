package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.Question;

public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(String id);
    List<Question> findAll();
    List<Question> findByCategoryId(String categoryId);
    void deleteById(String id);
}
