package saber.flow.com.example.demo.api.dto.response;

import java.util.List;

import saber.flow.com.example.demo.domain.enums.QuestionType;

public record QuestionResponse(
        String id,
        CategoryResponse category,
        QuestionType type,
        String question,
        List<OptionResponse> options
) {
}