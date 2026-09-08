package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.UserStudyLanguageEntity;

public interface UserStudyLanguageJpaRepository extends JpaRepository<UserStudyLanguageEntity, String> {
    List<UserStudyLanguageEntity> findByUser_Id(String userId);
    Optional<UserStudyLanguageEntity> findByUser_IdAndLanguage_Id(String userId, String languageId);
}