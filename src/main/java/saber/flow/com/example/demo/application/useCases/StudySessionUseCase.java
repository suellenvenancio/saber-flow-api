package saber.flow.com.example.demo.application.useCases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.StudySession;
import saber.flow.com.example.demo.domain.enums.StudySessionStatus;
import saber.flow.com.example.demo.domain.repository.CardRepository;
import saber.flow.com.example.demo.domain.repository.QuestionRepository;
import saber.flow.com.example.demo.domain.repository.StudySessionRepository;

@Service
@RequiredArgsConstructor
public class StudySessionUseCase {

    private final StudySessionRepository studySessionRepository;
    private final CardRepository cardRepository;
    private final QuestionRepository questionRepository;

    public StudySession save(StudySession studySession) {
      StudySession sessionToSave = prepareForSave(studySession);

      if (!sessionToSave.getCardIds().isEmpty()) {
        sessionToSave.getCardIds().forEach(card -> {
          cardRepository.findById(card).orElseThrow(() -> new RuntimeException("Card not found: " + card));
        }); 
      }
    
      if (!sessionToSave.getQuestionIds().isEmpty()) {
        sessionToSave.getQuestionIds().forEach(question -> {
          questionRepository.findById(question)
              .orElseThrow(() -> new RuntimeException("Question not found: " + question));
        });
      }
     
      return studySessionRepository.save(sessionToSave);
    }

    private StudySession prepareForSave(StudySession studySession) {
      StudySession existing = studySession.getId() == null
          ? null
          : studySessionRepository.findById(studySession.getId()).orElse(null);

      LocalDateTime startedAt = existing != null ? existing.getStartedAt() : studySession.getStartedAt();
      StudySessionStatus status = existing != null && existing.getStatus() != StudySessionStatus.ACTIVE
          ? existing.getStatus()
          : studySession.getStatus();
      LocalDateTime endedAt = existing != null && existing.getEndedAt() != null
          ? existing.getEndedAt()
          : studySession.getEndedAt();

      return new StudySession(studySession.getId(), studySession.getUserId(), startedAt, endedAt,
          status, studySession.getCardIds(), studySession.getQuestionIds());
    }

    public Optional<StudySession> findById(String id) {
        return studySessionRepository.findById(id);
    }

    public List<StudySession> findAll() {
        return studySessionRepository.findAll();
    }

    public List<StudySession> findByUserId(String userId) {
      return studySessionRepository.findByUserId(userId);
    }
    
    public void deleteById(String id) {
        studySessionRepository.deleteById(id);
    }
}
