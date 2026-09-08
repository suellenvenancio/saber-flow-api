package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.Language;

public interface LanguageRepository {
    Language save(Language language);
    Optional<Language> findById(String id);
    List<Language> findAll();
    void deleteById(String id);
}
