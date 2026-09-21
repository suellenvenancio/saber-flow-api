package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Question;

public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(String id);
    List<Question> findAll(); 
    void deleteById(String id);

    List<Question> findByCategoryIdsAndLevel(List<String> categoryIds, Level level, int size);

    List<Question> findCardByLanguageIdAndLevel(String languageId, Level level, int size);

    List<Question> findByCategoryId(String categoryId, int size);
    
    List<Question> findAllByCategoryId(String categoryId);

    List<Question> findCardByLanguageId(String languageId, int size);
}
