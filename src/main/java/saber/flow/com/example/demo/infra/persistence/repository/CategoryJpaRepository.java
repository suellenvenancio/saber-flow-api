package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.CategoryEntity;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {
	List<CategoryEntity> findByLanguage_Id(String languageId);
}