package saber.flow.com.example.demo.api.controllers;

import java.util.Collections;
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
import saber.flow.com.example.demo.api.dto.request.QuestionRequest;
import saber.flow.com.example.demo.api.dto.response.CategoryResponse;
import saber.flow.com.example.demo.api.dto.response.LanguageResponse;
import saber.flow.com.example.demo.api.dto.response.OptionResponse;
import saber.flow.com.example.demo.api.dto.response.QuestionResponse;
import saber.flow.com.example.demo.application.useCases.QuestionUseCase;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.model.Language;
import saber.flow.com.example.demo.domain.model.Option;
import saber.flow.com.example.demo.domain.model.Question;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionUseCase questionUseCase;

    @PostMapping
    public ResponseEntity<QuestionResponse> save(@RequestBody QuestionRequest request) {
        Question question = new Question(
                request.id(),
                buildCategoryReference(request.categoryId()),
                request.type(),
                request.question(),
                Collections.emptyList());
        Question saved = questionUseCase.save(question);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable String id) {
        return questionUseCase.findById(id)
                .map(question -> ResponseEntity.ok(toResponse(question)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> findAll(
            @RequestParam(required = false) String categoryId
    ) {
        List<Question> questions = categoryId != null && !categoryId.isBlank()
                ? questionUseCase.findByCategoryId(categoryId)
                : questionUseCase.findAll();

        return ResponseEntity.ok(questions.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        questionUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Category buildCategoryReference(String categoryId) {
        return new Category(
                categoryId,
                "placeholder-category",
                new Language("placeholder-language-id", "placeholder-language"));
    }

    private QuestionResponse toResponse(Question question) {
        Category category = question.getCategory();
        Language language = category.getLanguage();
        LanguageResponse languageResponse = new LanguageResponse(language.getId(), language.getLanguage());
        CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName(), languageResponse);

        List<OptionResponse> options = question.getOptions().stream()
                .map(this::toOptionResponse)
                .toList();

        return new QuestionResponse(
                question.getId(),
                categoryResponse,
                question.getType(),
                question.getQuestion(),
                options);
    }

    private OptionResponse toOptionResponse(Option option) {
        return new OptionResponse(option.getId(), option.getOption(), option.getIsCorrect(), option.getQuestionId());
    }
}