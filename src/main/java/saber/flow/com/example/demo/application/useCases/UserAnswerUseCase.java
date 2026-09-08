package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.UserAnswer;
import saber.flow.com.example.demo.domain.repository.UserAnswerRepository;

@Service
@RequiredArgsConstructor
public class UserAnswerUseCase {

    private final UserAnswerRepository userAnswerRepository;

    public UserAnswer save(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    public Optional<UserAnswer> findById(String id) {
        return userAnswerRepository.findById(id);
    }

    public List<UserAnswer> findAll() {
        return userAnswerRepository.findAll();
    }

    public List<UserAnswer> findByUserId(String userId) {
        return userAnswerRepository.findByUserId(userId);
    }

    public List<UserAnswer> findByQuestionId(String questionId) {
        return userAnswerRepository.findByQuestionId(questionId);
    }

    public void deleteById(String id) {
        userAnswerRepository.deleteById(id);
    }
}