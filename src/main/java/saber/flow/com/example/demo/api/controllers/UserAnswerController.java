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
import saber.flow.com.example.demo.api.dto.request.UserAnswerRequest;
import saber.flow.com.example.demo.api.dto.response.UserAnswerResponse;
import saber.flow.com.example.demo.application.useCases.UserAnswerUseCase;
import saber.flow.com.example.demo.domain.model.UserAnswer;

@RestController
@RequestMapping("/api/user-answers")
@RequiredArgsConstructor
public class UserAnswerController {

    private final UserAnswerUseCase userAnswerUseCase;

    @PostMapping
    public ResponseEntity<UserAnswerResponse> save(@RequestBody UserAnswerRequest request) {
        UserAnswer userAnswer = new UserAnswer(
                request.id(),
                request.userId(),
                request.questionId(),
                request.optionId(),
                request.providedAnswer(),
                request.isCorrect());
        UserAnswer saved = userAnswerUseCase.save(userAnswer);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAnswerResponse> findById(@PathVariable String id) {
        return userAnswerUseCase.findById(id)
                .map(userAnswer -> ResponseEntity.ok(toResponse(userAnswer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserAnswerResponse>> findAll(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String questionId
    ) {
        if (userId != null && questionId != null) {
            return ResponseEntity.badRequest().build();
        }

        List<UserAnswer> answers;
        if (userId != null && !userId.isBlank()) {
            answers = userAnswerUseCase.findByUserId(userId);
        } else if (questionId != null && !questionId.isBlank()) {
            answers = userAnswerUseCase.findByQuestionId(questionId);
        } else {
            answers = userAnswerUseCase.findAll();
        }

        return ResponseEntity.ok(answers.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userAnswerUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserAnswerResponse toResponse(UserAnswer userAnswer) {
        return new UserAnswerResponse(
                userAnswer.getId(),
                userAnswer.getUserId(),
                userAnswer.getQuestionId(),
                userAnswer.getOptionId(),
                userAnswer.getProvidedAnswer(),
                userAnswer.isCorrect(),
                userAnswer.getAnsweredAt());
    }
}