package saber.flow.com.example.demo.api.dto.request;

import saber.flow.com.example.demo.domain.enums.Level;

public record CardRequest(
        String id,
        String question,
        String answer,
        String categoryId,
        Level level
) {
}