package saber.flow.com.example.demo.api.dto.request;

import java.util.List;
import java.time.LocalDateTime;

import saber.flow.com.example.demo.domain.enums.StudySessionStatus;

public record StudySessionRequest(
        String id,
        String userId,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        List<String> cardIds,
        List<String> questionIds,
        StudySessionStatus status
) {
}
