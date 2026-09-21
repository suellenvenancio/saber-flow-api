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
import saber.flow.com.example.demo.api.dto.request.CardRequest;
import saber.flow.com.example.demo.api.dto.response.CardResponse;
import saber.flow.com.example.demo.api.dto.response.CategoryResponse;
import saber.flow.com.example.demo.api.dto.response.LanguageResponse;
import saber.flow.com.example.demo.application.useCases.AdaptiveCardUseCase;
import saber.flow.com.example.demo.application.useCases.CardUseCase;
import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardUseCase cardUseCase;
    private final AdaptiveCardUseCase adaptiveCardUseCase;

    @PostMapping
    public ResponseEntity<CardResponse> save(@RequestBody CardRequest request) {
        Card card = new Card(
                request.id(),
                request.question(),
                request.answer(),
                null,
                null,
                buildCategoryReference(request.categoryId()),
                request.level());
        Card saved = cardUseCase.save(card);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable String id) {
        return cardUseCase.findById(id)
                .map(card -> ResponseEntity.ok(toResponse(card)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> findAll(
            @RequestParam(required = false) List<String> categoryIds,
            @RequestParam(required = true) String languageId,
            @RequestParam(required = false) Level level,
            @RequestParam(required = true) int size,
            @RequestParam(required = false) String userId
    ) {
        if (languageId == null || size <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = adaptiveCardUseCase.findAdaptive(categoryIds, languageId, level, size, userId);

        return ResponseEntity.ok(cards.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        cardUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Category buildCategoryReference(String categoryId) {
        return new Category(
                categoryId,
                "placeholder-category",
                new Language("placeholder-language-id", "placeholder-language"));
    }

    private CardResponse toResponse(Card card) {
        Language language = card.getCategory().getLanguage();
        LanguageResponse languageResponse = new LanguageResponse(language.getId(), language.getLanguage());
        Category category = card.getCategory();
        CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName(), languageResponse);
        return new CardResponse(card.getId(), card.getQuestion(), card.getAnswer(), categoryResponse, card.getLevel());
    }
}