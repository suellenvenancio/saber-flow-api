package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.Setter;
import saber.flow.com.example.demo.domain.enums.Level;

@Getter
@Setter
public class Card {
  private String id;
  private String question;
  private String answer;
  private String exemplo;
  private String exemploTranslate;
  private Category category;
  private Level level;

  public Card(String id, String question, String answer, String exemplo, String exemploTranslate, Category category, Level level) {
    if (question == null || question.isEmpty()) {
      throw new IllegalArgumentException("Question cannot be null or empty");
    }
    if (answer == null || answer.isEmpty()) {
      throw new IllegalArgumentException("Answer cannot be null or empty");
    }
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    if (level == null) {
      throw new IllegalArgumentException("Level cannot be null");
    }

    this.id = id;
    this.question = question;
    this.answer = answer;
    this.exemplo = exemplo;
    this.exemploTranslate = exemploTranslate;
    this.category = category;
    this.level = level;
  }
}
