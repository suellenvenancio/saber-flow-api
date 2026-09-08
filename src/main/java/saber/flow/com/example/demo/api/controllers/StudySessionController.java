package saber.flow.com.example.demo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.api.dto.request.StudySessionRequest;
import saber.flow.com.example.demo.api.dto.response.StudySessionResponse;
import saber.flow.com.example.demo.application.useCases.StudySessionUseCase;
import saber.flow.com.example.demo.domain.model.StudySession;

@RestController
@RequestMapping("/api/study-sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionUseCase studySessionUseCase;

    @PostMapping
    public ResponseEntity<StudySessionResponse> save(@RequestBody StudySessionRequest request) {
        StudySession studySession = new StudySession(
                request.id(),
                request.userId(),
                request.startedAt(),
                request.endedAt(),
                request.status(),
                request.cardIds(),
                request.questionIds());
        StudySession saved = studySessionUseCase.save(studySession);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudySessionResponse> findById(@PathVariable String id) {
        return studySessionUseCase.findById(id)
                .map(studySession -> ResponseEntity.ok(toResponse(studySession)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponse>> findAll(
            @RequestParam(required = false) String userId
    ) {
        List<StudySession> sessions = userId != null && !userId.isBlank()
                ? studySessionUseCase.findByUserId(userId)
                : studySessionUseCase.findAll();

        return ResponseEntity.ok(sessions.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        studySessionUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private StudySessionResponse toResponse(StudySession studySession) {
        return new StudySessionResponse(
                studySession.getId(),
                studySession.getUserId(),
                studySession.getStartedAt(),
                studySession.getEndedAt(),
                studySession.getStatus(),
                studySession.getCardIds(),
                studySession.getQuestionIds());
    }
}
