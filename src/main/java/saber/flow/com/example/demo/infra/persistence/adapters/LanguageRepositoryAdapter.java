package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.repository.LanguageRepository;
import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;
import saber.flow.com.example.demo.infra.persistence.repository.LanguageJpaRepository;

@Repository
@RequiredArgsConstructor
public class LanguageRepositoryAdapter implements LanguageRepository {

    private final LanguageJpaRepository languageJpaRepository;

    @Override
    public Language save(Language language) {
        LanguageEntity entity = new LanguageEntity(language.getId(), language.getLanguage());
        return toDomain(languageJpaRepository.save(entity));
    }

    @Override
    public Optional<Language> findById(String id) {
        return languageJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Language> findAll() {
        return languageJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        languageJpaRepository.deleteById(id);
    }

    private Language toDomain(LanguageEntity entity) {
        return new Language(entity.getId(), entity.getLanguage());
    }
}