package saber.flow.com.example.demo.api.dto.response;

public record CategoryResponse(
        String id,
        String name,
        LanguageResponse language
) {
}