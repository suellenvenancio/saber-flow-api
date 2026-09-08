package saber.flow.com.example.demo.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import saber.flow.com.example.demo.domain.enums.StudySessionStatus;

@Getter
@ToString
public class StudySession {
  private final String id;
  private final String userId;
  private final LocalDateTime startedAt;
  private final LocalDateTime endedAt;
  private final StudySessionStatus status;
  private final List<String> cardIds;
  private final List<String> questionIds;

  public StudySession(String id, String userId, List<String> cardIds, List<String> questionIds) {
    this(id, userId, LocalDateTime.now(), null, StudySessionStatus.ACTIVE, cardIds, questionIds);
  }

  public StudySession(String id, String userId, LocalDateTime startedAt, LocalDateTime endedAt,
      StudySessionStatus status, List<String> cardIds, List<String> questionIds) {
    if (userId == null || userId.isBlank()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    if (startedAt == null) {
      throw new IllegalArgumentException("Started at cannot be null");
    }
    if (status != null && status != StudySessionStatus.ACTIVE && endedAt == null) {
      throw new IllegalArgumentException("Ended at cannot be null for a finished session");
    }

    this.id = id;
    this.userId = userId;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.status = status != null ? status : StudySessionStatus.ACTIVE;
    this.cardIds = cardIds != null ? List.copyOf(cardIds) : Collections.emptyList();
    this.questionIds = questionIds != null ? List.copyOf(questionIds) : Collections.emptyList();
  }
}
