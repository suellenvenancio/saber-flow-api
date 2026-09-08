package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
  private String id;
  private String name;
  private Language language;

  public Category(String id, String name, Language language) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (language == null) {
      throw new IllegalArgumentException("Language cannot be null");
    }
    this.id = id;
    this.name = name;
    this.language = language;
  }
}
