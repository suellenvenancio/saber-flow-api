package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.repository.LanguageRepository;

@Service
@RequiredArgsConstructor
public class LanguageUseCase {

    private final LanguageRepository languageRepository;

    public Language save(Language language) {
        return languageRepository.save(language);
    }

    public Optional<Language> findById(String id) {
        return languageRepository.findById(id);
    }

    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    public void deleteById(String id) {
        languageRepository.deleteById(id);
    }
}