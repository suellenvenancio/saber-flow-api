package saber.flow.com.example.demo.infra.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saber.flow.com.example.demo.domain.enums.StudySessionStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "study_sessions")
public class StudySessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16, columnDefinition = "varchar(16) default 'ACTIVE'")
    private StudySessionStatus status = StudySessionStatus.ACTIVE;

    @ElementCollection
    @CollectionTable(name = "study_session_cards", joinColumns = @JoinColumn(name = "study_session_id"))
    @Column(name = "card_id", nullable = false, length = 64)
    private List<String> cardIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "study_session_questions", joinColumns = @JoinColumn(name = "study_session_id"))
    @Column(name = "question_id", nullable = false, length = 64)
    private List<String> questionIds = new ArrayList<>();
}
