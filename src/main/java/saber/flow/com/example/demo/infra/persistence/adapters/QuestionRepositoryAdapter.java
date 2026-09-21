package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.model.Option;
import saber.flow.com.example.demo.domain.model.Question;
import saber.flow.com.example.demo.domain.repository.QuestionRepository;
import saber.flow.com.example.demo.infra.persistence.entities.CategoryEntity;
import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;
import saber.flow.com.example.demo.infra.persistence.entities.OptionEntity;
import saber.flow.com.example.demo.infra.persistence.entities.QuestionEntity;
import saber.flow.com.example.demo.infra.persistence.repository.CategoryJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.OptionJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.QuestionJpaRepository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryAdapter implements QuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final OptionJpaRepository optionJpaRepository;

    @Override
    public Question save(Question question) {
        String categoryId = question.getCategory().getId();
        CategoryEntity categoryEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        QuestionEntity entity = new QuestionEntity(question.getId(), categoryEntity, question.getType(), question.getQuestion());
        return toDomain(questionJpaRepository.save(entity));
    }

    @Override
    public Optional<Question> findById(String id) {
        return questionJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Question> findAll() {
        return questionJpaRepository.findAll().stream().map(this::toDomain).toList();
    } 
    @Override
    public void deleteById(String id) {
        questionJpaRepository.deleteById(id);
    }

    @Override
    public List<Question> findByCategoryIdsAndLevel(List<String> categoryIds, Level level, int size) { 
      List<Question> result = new ArrayList<>();
      categoryIds.forEach(categoryId -> {
        List<Question> questions = questionJpaRepository.findByCategory_IdAndLevel(categoryId, level.name()).stream()
                .map(this::toDomain)
                .limit(size)
            .toList();
        result.addAll(questions);
      });
      return result;
    } 

    @Override
    public List<Question> findCardByLanguageIdAndLevel(String languageId, Level level, int size) {
        return questionJpaRepository.findByCategory_Language_IdAndLevel(languageId, level.name()).stream()
                .map(this::toDomain)
                .limit(size)
                .toList();
    }

    @Override
    public List<Question> findByCategoryId(String categoryId, int size) { 
        return questionJpaRepository.findByCategory_Id(categoryId).stream()
                .map(this::toDomain)
                .limit(size)
                .toList();
    }

    @Override
    public List<Question> findCardByLanguageId(String languageId, int size) {
      return questionJpaRepository.findByCategory_Language_Id(languageId).stream()
          .map(this::toDomain)
          .limit(size)
          .toList();
    }
    
    @Override
    public List<Question> findAllByCategoryId(String categoryId) {
      return questionJpaRepository.findByCategory_Id(categoryId).stream().map(this::toDomain).toList();
    }

    private Question toDomain(QuestionEntity entity) {
        CategoryEntity categoryEntity = entity.getCategory();
        LanguageEntity languageEntity = categoryEntity.getLanguage();
        Language language = new Language(languageEntity.getId(), languageEntity.getLanguage());
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName(), language);
        List<Option> options = optionJpaRepository.findByQuestion_Id(entity.getId()).stream()
                .map(this::toDomainOption)
                .toList();

        return new Question(entity.getId(), category, entity.getType(), entity.getQuestion(), options);
    }

    private Option toDomainOption(OptionEntity entity) {
        return new Option(entity.getId(), entity.getOption(), entity.getIsCorrect(), entity.getQuestion().getId());
    } 
}