package saber.flow.com.example.demo.infra.persistence.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.StudySessionEntity;

public interface StudySessionJpaRepository extends JpaRepository<StudySessionEntity, String> {
  List<StudySessionEntity> findByUser_Id(String userId);
}
