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
import saber.flow.com.example.demo.api.dto.request.UserStudyLanguageRequest;
import saber.flow.com.example.demo.api.dto.response.UserStudyLanguageResponse;
import saber.flow.com.example.demo.application.useCases.UserStudyLanguageUseCase;
import saber.flow.com.example.demo.domain.model.UserStudyLanguage;

@RestController
@RequestMapping("/api/user-study-languages")
@RequiredArgsConstructor
public class UserStudyLanguageController {

    private final UserStudyLanguageUseCase userStudyLanguageUseCase;

    @PostMapping
    public ResponseEntity<UserStudyLanguageResponse> save(@RequestBody UserStudyLanguageRequest request) {
        UserStudyLanguage saved = userStudyLanguageUseCase.save(
                new UserStudyLanguage(request.id(), request.userId(), request.languageId()));
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStudyLanguageResponse> findById(@PathVariable String id) {
        return userStudyLanguageUseCase.findById(id)
                .map(userStudyLanguage -> ResponseEntity.ok(toResponse(userStudyLanguage)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserStudyLanguageResponse>> findAll(
            @RequestParam(required = false) String userId
    ) {
        if (userId != null && !userId.isBlank()) {
            return ResponseEntity.ok(
                    userStudyLanguageUseCase.findByUserId(userId).stream().map(this::toResponse).toList());
        }

        return ResponseEntity.ok(userStudyLanguageUseCase.findAll().stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userStudyLanguageUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserStudyLanguageResponse toResponse(UserStudyLanguage userStudyLanguage) {
        return new UserStudyLanguageResponse(
                userStudyLanguage.getId(),
                userStudyLanguage.getUserId(),
                userStudyLanguage.getLanguageId());
    }
}