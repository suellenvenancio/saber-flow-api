package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.QuestionEntity;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, String> {
  List<QuestionEntity> findByCategory_Id(String categoryId);

  List<QuestionEntity> findByCategory_Language_Id(String languageId);
}