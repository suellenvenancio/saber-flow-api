package saber.flow.com.example.demo.api.dto.request;

public record UserAnswerRequest(
        String id,
        String userId,
        String questionId,
        String optionId,
        String providedAnswer,
        boolean isCorrect
) {
}