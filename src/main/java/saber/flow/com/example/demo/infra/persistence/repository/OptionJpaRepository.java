package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.OptionEntity;

public interface OptionJpaRepository extends JpaRepository<OptionEntity, String> {
	List<OptionEntity> findByQuestion_Id(String questionId);
}