package saber.flow.com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserAnswer {
  private final String id;
  private final String userId;
  private final String questionId;
  private final String  optionId;
  private final String providedAnswer;
  private final boolean isCorrect;
  private final LocalDateTime answeredAt;

  public UserAnswer(String id, String userId, String questionId, String optionId, String providedAnswer, boolean isCorrect) {
    if (userId == null || userId.isBlank())
      throw new IllegalArgumentException("User ID is required");
    if (questionId == null || questionId.isBlank())
      throw new IllegalArgumentException("Question ID is required");
    
    if (optionId != null && !optionId.isBlank() && providedAnswer != null && !providedAnswer.isBlank())
      throw new IllegalArgumentException("Cannot have both optionId and providedAnswer");

    if ((optionId == null || optionId.isBlank()) && (providedAnswer == null || providedAnswer.isBlank()))
      throw new IllegalArgumentException("Must have either optionId or providedAnswer");

    this.id = id;
    this.userId = userId;
    this.questionId = questionId;
    this.optionId = optionId;
    this.providedAnswer = providedAnswer;
    this.isCorrect = isCorrect;
    this.answeredAt = LocalDateTime.now();
  }
}