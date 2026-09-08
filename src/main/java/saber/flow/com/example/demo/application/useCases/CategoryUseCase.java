package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.Category;
import saber.flow.com.example.demo.domain.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByLanguageId(String languageId) {
        return categoryRepository.findByLanguageId(languageId);
    }

    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }
}