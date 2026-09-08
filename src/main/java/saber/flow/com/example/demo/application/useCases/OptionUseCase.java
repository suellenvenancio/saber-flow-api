package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Option;
import saber.flow.com.example.demo.domain.repository.OptionRepository;

@Service
@RequiredArgsConstructor
public class OptionUseCase {

    private final OptionRepository optionRepository;

    public Option save(Option option) {
        return optionRepository.save(option);
    }

    public Optional<Option> findById(String id) {
        return optionRepository.findById(id);
    }

    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    public List<Option> findByQuestionId(String questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    public void deleteById(String id) {
        optionRepository.deleteById(id);
    }
}