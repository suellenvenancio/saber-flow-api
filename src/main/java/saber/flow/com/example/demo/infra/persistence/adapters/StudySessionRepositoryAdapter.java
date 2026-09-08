package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.StudySession;
import saber.flow.com.example.demo.domain.repository.StudySessionRepository;
import saber.flow.com.example.demo.infra.persistence.entities.StudySessionEntity;
import saber.flow.com.example.demo.infra.persistence.entities.UserEntity;
import saber.flow.com.example.demo.infra.persistence.repository.StudySessionJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.UserJpaRepository;

@Repository
@RequiredArgsConstructor
public class StudySessionRepositoryAdapter implements StudySessionRepository {

    private final StudySessionJpaRepository studySessionJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public StudySession save(StudySession studySession) {
        String userId = studySession.getUserId();
        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        StudySessionEntity entity = new StudySessionEntity(
                studySession.getId(),
                userEntity,
                studySession.getStartedAt(),
                studySession.getEndedAt(),
                studySession.getStatus(),
                new ArrayList<>(studySession.getCardIds()),
                new ArrayList<>(studySession.getQuestionIds()));

        return toDomain(studySessionJpaRepository.save(entity));
    }

    @Override
    public Optional<StudySession> findById(String id) {
        return studySessionJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<StudySession> findAll() {
        return studySessionJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<StudySession> findByUserId(String userId) {
        return studySessionJpaRepository.findByUser_Id(userId).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
      studySessionJpaRepository.deleteById(id);
    }
    
    private StudySession toDomain(StudySessionEntity entity) {
        return new StudySession(entity.getId(), entity.getUser().getId(), entity.getStartedAt(),
                entity.getEndedAt(), entity.getStatus(), entity.getCardIds(), entity.getQuestionIds());
    }
}
