package saber.flow.com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Progress {
  private final String userId;
  private final String categoryId;
  private final int correctAnswersCount;
  private final int totalAttempts;
  private final LocalDateTime lastUpdated;

  public Progress(String userId, String categoryId, int correctAnswersCount, int totalAttempts) {
    this.userId = userId;
    this.categoryId = categoryId;
    this.correctAnswersCount = correctAnswersCount;
    this.totalAttempts = totalAttempts;
    this.lastUpdated = LocalDateTime.now();
  }

  public double getAccuracyRate() {
    if (totalAttempts == 0)
      return 0.0;
    return (double) correctAnswersCount / totalAttempts;
  }
}