package saber.flow.com.example.demo.infra.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.UserStudyLanguage;
import saber.flow.com.example.demo.domain.repository.UserStudyLanguageRepository;
import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;
import saber.flow.com.example.demo.infra.persistence.entities.UserEntity;
import saber.flow.com.example.demo.infra.persistence.entities.UserStudyLanguageEntity;
import saber.flow.com.example.demo.infra.persistence.repository.LanguageJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.UserJpaRepository;
import saber.flow.com.example.demo.infra.persistence.repository.UserStudyLanguageJpaRepository;

@Repository
@RequiredArgsConstructor
public class UserStudyLanguageRepositoryAdapter implements UserStudyLanguageRepository {

    private final UserStudyLanguageJpaRepository userStudyLanguageJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LanguageJpaRepository languageJpaRepository;

    @Override
    public UserStudyLanguage save(UserStudyLanguage userStudyLanguage) {
        UserEntity userEntity = userJpaRepository.findById(userStudyLanguage.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userStudyLanguage.getUserId()));
        LanguageEntity languageEntity = languageJpaRepository.findById(userStudyLanguage.getLanguageId())
                .orElseThrow(() -> new IllegalArgumentException("Language not found: " + userStudyLanguage.getLanguageId()));

        UserStudyLanguageEntity entity = userStudyLanguage.getId() != null
                ? userStudyLanguageJpaRepository.findById(userStudyLanguage.getId()).orElse(new UserStudyLanguageEntity())
                : userStudyLanguageJpaRepository
                        .findByUser_IdAndLanguage_Id(userStudyLanguage.getUserId(), userStudyLanguage.getLanguageId())
                        .orElse(new UserStudyLanguageEntity());

        entity.setUser(userEntity);
        entity.setLanguage(languageEntity);

        return toDomain(userStudyLanguageJpaRepository.save(entity));
    }

    @Override
    public Optional<UserStudyLanguage> findById(String id) {
        return userStudyLanguageJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<UserStudyLanguage> findByUserId(String userId) {
        return userStudyLanguageJpaRepository.findByUser_Id(userId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UserStudyLanguage> findAll() {
        return userStudyLanguageJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        userStudyLanguageJpaRepository.deleteById(id);
    }

    private UserStudyLanguage toDomain(UserStudyLanguageEntity entity) {
        return new UserStudyLanguage(entity.getId(), entity.getUser().getId(), entity.getLanguage().getId());
    }
}