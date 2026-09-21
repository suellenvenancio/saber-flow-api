package saber.flow.com.example.demo.application.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Question;
import saber.flow.com.example.demo.domain.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class QuestionUseCase {

    private final QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Optional<Question> findById(String id) {
        return questionRepository.findById(id);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findByCategoryId(String categoryId) {
        return questionRepository.findAllByCategoryId(categoryId);
    }

    public void deleteById(String id) {
      questionRepository.deleteById(id);
    }
    
    public List<Question> findByCategoryIds(List<String> categoryIds, int size) {
      List<Question> result = new ArrayList<>(); 

      int questionsPerCategory = (int) Math.round((double) size / categoryIds.size());

      categoryIds.forEach(id -> {
        List<Question> question = questionRepository.findByCategoryId(id, questionsPerCategory);
        result.addAll(question.stream().limit(questionsPerCategory).toList());
      });

      return result;
    }
    
    public List<Question> findCardByLanguageId(String languageId, int size) {
      return questionRepository.findCardByLanguageId(languageId, size);
    }
}