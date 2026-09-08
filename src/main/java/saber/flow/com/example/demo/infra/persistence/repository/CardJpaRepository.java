package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.infra.persistence.entities.CardEntity;

public interface CardJpaRepository extends JpaRepository<CardEntity, String> {
	List<CardEntity> findByCategory_Id(String categoryId);

	List<CardEntity> findByCategory_IdAndLevel(String categoryId, Level level);

	List<CardEntity> findByCategory_Language_IdAndLevel(String languageId, Level level);

	List<CardEntity> findByCategory_Language_Id(String languageId);

    List<CardEntity> findByCategory_IdAndLevelAndCategory_Language_Id(String categoryId, Level level,
        String languageId);
  
  List<CardEntity> findByCategory_IdAndCategory_Language_Id(String categoryId, String languageId);
}