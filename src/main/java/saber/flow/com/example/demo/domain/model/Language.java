package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Language {
  private String id;
  private String language;

  public Language(String id, String language) {
    if (language == null || language.isEmpty()) {
      throw new IllegalArgumentException("Language cannot be null or empty");
    }

    this.id = id;
    this.language = language;
  }
}
