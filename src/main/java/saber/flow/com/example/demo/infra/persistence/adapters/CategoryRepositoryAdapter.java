package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.repository.CategoryRepository;
import saber.flow.com.example.demo.infra.persistence.entities.CategoryEntity;
import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;
import saber.flow.com.example.demo.infra.persistence.repository.CategoryJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.LanguageJpaRepository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final LanguageJpaRepository languageJpaRepository;

    @Override
    public Category save(Category category) {
        String languageId = category.getLanguage().getId();
        LanguageEntity languageEntity = languageJpaRepository.findById(languageId)
                .orElseThrow(() -> new IllegalArgumentException("Language not found: " + languageId));

        CategoryEntity entity = new CategoryEntity(category.getId(), category.getName(), languageEntity);
        return toDomain(categoryJpaRepository.save(entity));
    }

    @Override
    public Optional<Category> findById(String id) {
        return categoryJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Category> findByLanguageId(String languageId) {
        return categoryJpaRepository.findByLanguage_Id(languageId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        categoryJpaRepository.deleteById(id);
    }

    private Category toDomain(CategoryEntity entity) {
        LanguageEntity languageEntity = entity.getLanguage();
        Language language = new Language(languageEntity.getId(), languageEntity.getLanguage());
        return new Category(entity.getId(), entity.getName(), language);
    }
}