package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.QuestionShortAnswerEntity;

public interface QuestionShortAnswerJpaRepository extends JpaRepository<QuestionShortAnswerEntity, String> {
    List<QuestionShortAnswerEntity> findByQuestion_Id(String questionId);
}