package saber.flow.com.example.demo.infra.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "progress",
    uniqueConstraints = @UniqueConstraint(name = "uk_progress_user_category", columnNames = {"user_id", "category_id"})
)
public class ProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "correct_answers_count", nullable = false)
    private int correctAnswersCount;

    @Column(name = "total_attempts", nullable = false)
    private int totalAttempts;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
