package saber.flow.com.example.demo.domain.model;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import saber.flow.com.example.demo.domain.enums.QuestionType;

@Getter
@ToString
public class Question {
  private final String id;
  private final Category category; 
  private final QuestionType type;
  private final String question;
  private final List<Option> options;

  public Question(String id, Category category, QuestionType type, String question, List<Option> options) {

    if (category == null) {
      throw new IllegalArgumentException("Question must belong to a category/subject");
    }
    if (type == null) {
      throw new IllegalArgumentException("Question type cannot be null");
    }

    if (question == null || question.isEmpty()) {
      throw new IllegalArgumentException("Prompt text cannot be null or empty");
    }

    this.id = id;
    this.category = category;
    this.type = type;
    this.question = question;
    this.options = options != null ? List.copyOf(options) : Collections.emptyList();
  } 
}