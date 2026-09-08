package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.repository.CardRepository;
import saber.flow.com.example.demo.infra.persistence.entities.CardEntity;
import saber.flow.com.example.demo.infra.persistence.entities.CategoryEntity;
import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;
import saber.flow.com.example.demo.infra.persistence.repository.CardJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.CategoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class CardRepositoryAdapter implements CardRepository {

    private final CardJpaRepository cardJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Card save(Card card) {
        String categoryId = card.getCategory().getId();
        CategoryEntity categoryEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        CardEntity entity = new CardEntity(card.getId(), card.getQuestion(), card.getAnswer(), card.getExemplo(), card.getExemploTranslate(), categoryEntity, card.getLevel());
        return toDomain(cardJpaRepository.save(entity));
    }

    @Override
    public Optional<Card> findById(String id) {
        return cardJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Card> findAll() {
        return cardJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Card> findByCategoryId(String categoryId) {
        return cardJpaRepository.findByCategory_Id(categoryId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        cardJpaRepository.deleteById(id);
    }

    private Card toDomain(CardEntity entity) {
        CategoryEntity categoryEntity = entity.getCategory();
        LanguageEntity languageEntity = categoryEntity.getLanguage();
        Language language = new Language(
            languageEntity.getId(),
            languageEntity.getLanguage()
          );
        Category category = new Category(
            categoryEntity.getId(),
            categoryEntity.getName(),
            language
          );

        return new Card(
            entity.getId(),
            entity.getQuestion(),
            entity.getAnswer(),
            entity.getExemplo(),
            entity.getExemploTranslate(),
            category, entity.getLevel()
          );
    }

    @Override
    public List<Card> findByCategoryIdAndLevel(String categoryId, Level level) {
        return cardJpaRepository.findByCategory_IdAndLevel(categoryId, level)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Card> findCardByLanguageIdAndLevel(String languageId, Level level) {
        return cardJpaRepository.findByCategory_Language_IdAndLevel(languageId, level)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Card> findCardByLanguageId(String languageId) {
        return cardJpaRepository.findByCategory_Language_Id(languageId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Card> findByCategoryIdAndLevelAndLanguageId(String categoryId, Level level, String languageId) {
      return cardJpaRepository.findByCategory_IdAndLevelAndCategory_Language_Id(categoryId, level, languageId)
          .stream()
          .map(this::toDomain)
          .toList();
    }
    
    @Override
    public List<Card> findByCategoryIdAndLanguageId(String categoryId, String languageId) {
        return cardJpaRepository.findByCategory_IdAndCategory_Language_Id(categoryId, languageId)
                .stream()
                .map(this::toDomain)
                .toList();
    }
}