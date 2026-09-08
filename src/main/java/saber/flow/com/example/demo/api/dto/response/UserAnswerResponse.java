package saber.flow.com.example.demo.api.dto.response;

import java.time.LocalDateTime;

public record UserAnswerResponse(
        String id,
        String userId,
        String questionId,
        String optionId,
        String providedAnswer,
        boolean isCorrect,
        LocalDateTime answeredAt
) {
}