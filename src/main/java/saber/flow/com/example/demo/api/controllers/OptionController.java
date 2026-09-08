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
import saber.flow.com.example.demo.api.dto.request.OptionRequest;
import saber.flow.com.example.demo.api.dto.response.OptionResponse;
import saber.flow.com.example.demo.application.useCases.OptionUseCase;
import saber.flow.com.example.demo.domain.model.Option;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionUseCase optionUseCase;

    @PostMapping
    public ResponseEntity<OptionResponse> save(@RequestBody OptionRequest request) {
        Option option = new Option(request.id(), request.option(), request.isCorrect(), request.questionId());
        Option saved = optionUseCase.save(option);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionResponse> findById(@PathVariable String id) {
        return optionUseCase.findById(id)
                .map(option -> ResponseEntity.ok(toResponse(option)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> findAll(
            @RequestParam(required = false) String questionId
    ) {
        List<Option> options = questionId != null && !questionId.isBlank()
                ? optionUseCase.findByQuestionId(questionId)
                : optionUseCase.findAll();

        return ResponseEntity.ok(options.stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        optionUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private OptionResponse toResponse(Option option) {
        return new OptionResponse(option.getId(), option.getOption(), option.getIsCorrect(), option.getQuestionId());
    }
}