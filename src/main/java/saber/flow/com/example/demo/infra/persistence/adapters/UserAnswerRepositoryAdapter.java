package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.UserAnswer;
import saber.flow.com.example.demo.domain.repository.UserAnswerRepository;
import saber.flow.com.example.demo.infra.persistence.entities.OptionEntity;
import saber.flow.com.example.demo.infra.persistence.entities.QuestionEntity;
import saber.flow.com.example.demo.infra.persistence.entities.UserAnswerEntity;
import saber.flow.com.example.demo.infra.persistence.entities.UserEntity;
import saber.flow.com.example.demo.infra.persistence.repository.OptionJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.QuestionJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.UserAnswerJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.UserJpaRepository;

@Repository
@RequiredArgsConstructor
public class UserAnswerRepositoryAdapter implements UserAnswerRepository {

    private final UserAnswerJpaRepository userAnswerJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final OptionJpaRepository optionJpaRepository;

    @Override
    public UserAnswer save(UserAnswer userAnswer) {
        UserEntity userEntity = userJpaRepository.findById(userAnswer.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userAnswer.getUserId()));

        QuestionEntity questionEntity = questionJpaRepository.findById(userAnswer.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found: " + userAnswer.getQuestionId()));

        OptionEntity optionEntity = null;
        if (userAnswer.getOptionId() != null && !userAnswer.getOptionId().isBlank()) {
            optionEntity = optionJpaRepository.findById(userAnswer.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("Option not found: " + userAnswer.getOptionId()));
        }

        UserAnswerEntity entity = new UserAnswerEntity(
                userAnswer.getId(),
                userEntity,
                questionEntity,
                optionEntity,
                userAnswer.getProvidedAnswer(),
                userAnswer.isCorrect(),
                userAnswer.getAnsweredAt());

        return toDomain(userAnswerJpaRepository.save(entity));
    }

    @Override
    public Optional<UserAnswer> findById(String id) {
        return userAnswerJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<UserAnswer> findAll() {
        return userAnswerJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<UserAnswer> findByUserId(String userId) {
        return userAnswerJpaRepository.findByUser_Id(userId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UserAnswer> findByQuestionId(String questionId) {
        return userAnswerJpaRepository.findByQuestion_Id(questionId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        userAnswerJpaRepository.deleteById(id);
    }

    private UserAnswer toDomain(UserAnswerEntity entity) {
        String optionId = entity.getOption() != null ? entity.getOption().getId() : null;
        return new UserAnswer(
                entity.getId(),
                entity.getUser().getId(),
                entity.getQuestion().getId(),
                optionId,
                entity.getProvidedAnswer(),
                entity.isCorrect());
    }
}