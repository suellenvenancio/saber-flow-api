package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.model.Category;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(String id);
    List<Category> findAll();
    List<Category> findByLanguageId(String languageId);
    void deleteById(String id);
}
