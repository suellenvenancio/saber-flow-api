package saber.flow.com.example.demo.api.dto.request;

import saber.flow.com.example.demo.domain.enums.QuestionType;

public record QuestionRequest(
        String id,
        String categoryId,
        QuestionType type,
        String question
) {
}