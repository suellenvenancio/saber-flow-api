package saber.flow.com.example.demo.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.QuestionShortAnswerKey;
import saber.flow.com.example.demo.domain.repository.QuestionShortAnswerRepository;

@Service
@RequiredArgsConstructor
public class QuestionShortAnswerUseCase {

    private final QuestionShortAnswerRepository questionShortAnswerRepository;

    public List<QuestionShortAnswerKey> findByQuestionId(String questionId) {
        return questionShortAnswerRepository.findByQuestionId(questionId);
    }
}