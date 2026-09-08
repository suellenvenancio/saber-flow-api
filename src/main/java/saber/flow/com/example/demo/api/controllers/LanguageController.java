package saber.flow.com.example.demo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.api.dto.request.LanguageRequest;
import saber.flow.com.example.demo.api.dto.response.LanguageResponse;
import saber.flow.com.example.demo.application.useCases.LanguageUseCase;
import saber.flow.com.example.demo.domain.model.Language;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageUseCase languageUseCase;

    @PostMapping
    public ResponseEntity<LanguageResponse> save(@RequestBody LanguageRequest request) {
        Language saved = languageUseCase.save(new Language(request.id(), request.language()));
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageResponse> findById(@PathVariable String id) {
        return languageUseCase.findById(id)
                .map(language -> ResponseEntity.ok(toResponse(language)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> findAll() {
        List<LanguageResponse> response = languageUseCase.findAll().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        languageUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LanguageResponse toResponse(Language language) {
        return new LanguageResponse(language.getId(), language.getLanguage());
    }
}