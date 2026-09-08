package saber.flow.com.example.demo.api.dto.request;

public record UserStudyLanguageRequest(
        String id,
        String userId,
        String languageId
) {
}