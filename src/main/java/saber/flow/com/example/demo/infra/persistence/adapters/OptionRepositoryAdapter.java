package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Option;
import saber.flow.com.example.demo.domain.repository.OptionRepository;
import saber.flow.com.example.demo.infra.persistence.entities.OptionEntity;
import saber.flow.com.example.demo.infra.persistence.entities.QuestionEntity;
import saber.flow.com.example.demo.infra.persistence.repository.OptionJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.QuestionJpaRepository;

@Repository
@RequiredArgsConstructor
public class OptionRepositoryAdapter implements OptionRepository {

    private final OptionJpaRepository optionJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public Option save(Option option) {
        String questionId = option.getQuestionId();
        QuestionEntity questionEntity = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionId));

        OptionEntity entity = new OptionEntity(option.getId(), option.getOption(), option.getIsCorrect(), questionEntity);
        return toDomain(optionJpaRepository.save(entity));
    }

    @Override
    public Optional<Option> findById(String id) {
        return optionJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Option> findAll() {
        return optionJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Option> findByQuestionId(String questionId) {
        return optionJpaRepository.findByQuestion_Id(questionId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        optionJpaRepository.deleteById(id);
    }

    private Option toDomain(OptionEntity entity) {
        return new Option(entity.getId(), entity.getOption(), entity.getIsCorrect(), entity.getQuestion().getId());
    }
}