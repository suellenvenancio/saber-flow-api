package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.Option;

public interface OptionRepository {
    Option save(Option option);
    Optional<Option> findById(String id);
    List<Option> findAll();
    List<Option> findByQuestionId(String questionId);
    void deleteById(String id);
}
