package saber.flow.com.example.demo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.api.dto.request.CategoryRequest;
import saber.flow.com.example.demo.api.dto.response.CategoryResponse;
import saber.flow.com.example.demo.api.dto.response.LanguageResponse;
import saber.flow.com.example.demo.application.useCases.CategoryUseCase;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request) {
        Category category = new Category(
                request.id(),
                request.name(),
                new Language(request.languageId(), "placeholder-language"));
        Category saved = categoryUseCase.save(category);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable String id) {
        return categoryUseCase.findById(id)
                .map(category -> ResponseEntity.ok(toResponse(category)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(
            @RequestParam(required = false) String languageId
    ) {
        List<Category> categories = languageId != null && !languageId.isBlank()
                ? categoryUseCase.findByLanguageId(languageId)
                : categoryUseCase.findAll();

        return ResponseEntity.ok(categories.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        categoryUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse toResponse(Category category) {
        Language language = category.getLanguage();
        LanguageResponse languageResponse = new LanguageResponse(language.getId(), language.getLanguage());
        return new CategoryResponse(category.getId(), category.getName(), languageResponse);
    }
}