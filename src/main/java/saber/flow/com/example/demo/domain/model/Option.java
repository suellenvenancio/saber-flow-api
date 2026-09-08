package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {
  private String id;
  private String option;
  private Boolean isCorrect;
  private String questionId;

  public Option(String id, String option, Boolean isCorrect, String questionId) {
    if (option == null || option.isEmpty()) {
      throw new IllegalArgumentException("Option text cannot be null or empty");
    }
    if (isCorrect == null) {
      throw new IllegalArgumentException("isCorrect cannot be null");
    }
    if (questionId == null || questionId.isEmpty()) {
      throw new IllegalArgumentException("Question ID cannot be null or empty");
    }

    this.id = id;
    this.option = option;
    this.isCorrect = isCorrect;
    this.questionId = questionId;
  }
}
