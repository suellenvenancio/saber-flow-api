package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionShortAnswerKey {
  private final String id;
  private final String questionId;
  private final String answerText; 

  public QuestionShortAnswerKey(
      String id,
      String questionId,
      String answerText
  ) {
    if (questionId == null || questionId.isBlank()) {
      throw new IllegalArgumentException("Question ID cannot be null or empty");
    }
    if (answerText == null || answerText.isBlank()) {
      throw new IllegalArgumentException("Answer text cannot be null or empty");
    } 

    this.id = id;
    this.questionId = questionId;
    this.answerText = answerText; 
  }
}