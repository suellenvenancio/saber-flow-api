package saber.flow.com.example.demo.domain.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserStudyLanguage {
  private final String id;
  private final String userId;
  private final String languageId;

  public UserStudyLanguage(String id, String userId, String languageId) {
    if (userId == null || userId.isBlank()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    if (languageId == null || languageId.isBlank()) {
      throw new IllegalArgumentException("Language ID cannot be null or empty");
    }

    this.id = id;
    this.userId = userId;
    this.languageId = languageId;
  }
}