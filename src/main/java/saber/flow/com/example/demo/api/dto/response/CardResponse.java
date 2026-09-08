package saber.flow.com.example.demo.api.dto.response;

import saber.flow.com.example.demo.domain.enums.Level;

public record CardResponse(
        String id,
        String question,
        String answer,
        CategoryResponse category,
        Level level
) {
}