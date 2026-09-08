package saber.flow.com.example.demo.api.dto.response;

public record QuestionShortAnswerResponse(
        String id,
        String questionId,
        String answerText
) {
}