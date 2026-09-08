package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.UserAnswerEntity;

public interface UserAnswerJpaRepository extends JpaRepository<UserAnswerEntity, String> {
	List<UserAnswerEntity> findByUser_Id(String userId);

	List<UserAnswerEntity> findByQuestion_Id(String questionId);
}