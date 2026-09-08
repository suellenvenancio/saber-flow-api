package saber.flow.com.example.demo.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.LanguageEntity;

public interface LanguageJpaRepository extends JpaRepository<LanguageEntity, String> {
}