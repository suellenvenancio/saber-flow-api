package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.QuestionShortAnswerKey;
import saber.flow.com.example.demo.domain.repository.QuestionShortAnswerRepository;
import saber.flow.com.example.demo.infra.persistence.entities.QuestionShortAnswerEntity;
import saber.flow.com.example.demo.infra.persistence.repository.QuestionShortAnswerJpaRepository;

@Repository
@RequiredArgsConstructor
public class QuestionShortAnswerRepositoryAdapter implements QuestionShortAnswerRepository {

    private final QuestionShortAnswerJpaRepository questionShortAnswerJpaRepository;

    @Override
    public List<QuestionShortAnswerKey> findByQuestionId(String questionId) {
        return questionShortAnswerJpaRepository.findByQuestion_Id(questionId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private QuestionShortAnswerKey toDomain(QuestionShortAnswerEntity entity) {
        return new QuestionShortAnswerKey(
                entity.getId(),
                entity.getQuestion().getId(),
                entity.getAnswerText());
    }
}