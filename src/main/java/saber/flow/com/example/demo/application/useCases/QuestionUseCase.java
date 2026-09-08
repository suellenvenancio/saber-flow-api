package saber.flow.com.example.demo.application.useCases;

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
        return questionRepository.findByCategoryId(categoryId);
    }

    public void deleteById(String id) {
        questionRepository.deleteById(id);
    }
}