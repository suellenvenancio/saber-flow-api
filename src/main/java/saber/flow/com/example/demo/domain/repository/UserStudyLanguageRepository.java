package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.UserStudyLanguage;

public interface UserStudyLanguageRepository {
    UserStudyLanguage save(UserStudyLanguage userStudyLanguage);
    Optional<UserStudyLanguage> findById(String id);
    List<UserStudyLanguage> findByUserId(String userId);
    List<UserStudyLanguage> findAll();
    void deleteById(String id);
}