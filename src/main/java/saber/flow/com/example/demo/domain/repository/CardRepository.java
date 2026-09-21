package saber.flow.com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(String id);
    List<Card> findAll();

    List<Card> findAllByCategoryId(String categoryId);
    List<Card> findByCategoryId(String categoryId, int size);
    List<Card> findByCategoryIdAndLevel(String categoryId, Level level, int size);
    List<Card> findCardByLanguageIdAndLevel(String languageId, Level level, int size);

    List<Card> findCardByLanguageId(String languageId, int size);
    List<Card> findAllByLanguageId(String languageId);
    void deleteById(String id);
}
