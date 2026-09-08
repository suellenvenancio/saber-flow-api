package saber.flow.com.example.demo.api.dto.request;

public record OptionRequest(
        String id,
        String option,
        Boolean isCorrect,
        String questionId
) {
}