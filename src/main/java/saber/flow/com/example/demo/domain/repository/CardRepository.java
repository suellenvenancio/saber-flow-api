package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(String id);
    List<Card> findAll();
    List<Card> findByCategoryId(String categoryId);
    List<Card> findByCategoryIdAndLanguageId(String categoryId, String languageId);
    List<Card> findByCategoryIdAndLevel(String categoryId, Level level);
    List<Card> findCardByLanguageIdAndLevel(String languageId, Level level);
    List<Card> findCardByLanguageId(String languageId);

    List<Card> findByCategoryIdAndLevelAndLanguageId(String categoryId, Level level, String languageId);
    
    void deleteById(String id);
}
