package saber.flow.com.example.demo.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import saber.flow.com.example.demo.domain.enums.StudySessionStatus;

public record StudySessionResponse(
        String id,
        String userId,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        StudySessionStatus status,
        List<String> cardIds,
        List<String> questionIds
) {
}
