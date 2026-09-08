package saber.flow.com.example.demo.api.dto.response;

public record OptionResponse(
        String id,
        String option,
        Boolean isCorrect,
        String questionId
) {
}