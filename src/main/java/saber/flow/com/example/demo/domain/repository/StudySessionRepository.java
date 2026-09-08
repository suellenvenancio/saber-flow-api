package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.StudySession;

public interface StudySessionRepository {
    StudySession save(StudySession studySession);
    Optional<StudySession> findById(String id);
    List<StudySession> findAll();
    List<StudySession> findByUserId(String userId);
    void deleteById(String id);
}
