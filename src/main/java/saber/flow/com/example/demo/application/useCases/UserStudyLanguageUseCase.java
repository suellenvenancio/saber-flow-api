package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.UserStudyLanguage;
import saber.flow.com.example.demo.domain.repository.UserStudyLanguageRepository;

@Service
@RequiredArgsConstructor
public class UserStudyLanguageUseCase {

    private final UserStudyLanguageRepository userStudyLanguageRepository;

    public UserStudyLanguage save(UserStudyLanguage userStudyLanguage) {
        return userStudyLanguageRepository.save(userStudyLanguage);
    }

    public Optional<UserStudyLanguage> findById(String id) {
        return userStudyLanguageRepository.findById(id);
    }

    public List<UserStudyLanguage> findByUserId(String userId) {
        return userStudyLanguageRepository.findByUserId(userId);
    }

    public List<UserStudyLanguage> findAll() {
        return userStudyLanguageRepository.findAll();
    }

    public void deleteById(String id) {
        userStudyLanguageRepository.deleteById(id);
    }
}