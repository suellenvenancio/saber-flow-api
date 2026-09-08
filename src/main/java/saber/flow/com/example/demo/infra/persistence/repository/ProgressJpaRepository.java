package saber.flow.com.example.demo.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.ProgressEntity;

public interface ProgressJpaRepository extends JpaRepository<ProgressEntity, String> {
}