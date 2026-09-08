package saber.flow.com.example.demo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.api.dto.response.QuestionShortAnswerResponse;
import saber.flow.com.example.demo.application.useCases.QuestionShortAnswerUseCase;
import saber.flow.com.example.demo.domain.model.QuestionShortAnswerKey;

@RestController
@RequestMapping("/api/question-short-answers")
@RequiredArgsConstructor
public class QuestionShortAnswerController {

    private final QuestionShortAnswerUseCase questionShortAnswerUseCase;

    @GetMapping
    public ResponseEntity<List<QuestionShortAnswerResponse>> findByQuestionId(
            @RequestParam String questionId
    ) {
        List<QuestionShortAnswerResponse> response = questionShortAnswerUseCase.findByQuestionId(questionId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    private QuestionShortAnswerResponse toResponse(QuestionShortAnswerKey key) {
        return new QuestionShortAnswerResponse(key.getId(), key.getQuestionId(), key.getAnswerText());
    }
}