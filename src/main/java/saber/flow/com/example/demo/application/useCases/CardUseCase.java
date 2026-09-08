package saber.flow.com.example.demo.application.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;
import saber.flow.com.example.demo.domain.repository.CardRepository;

@Service
@RequiredArgsConstructor
public class CardUseCase {

    private final CardRepository cardRepository;

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> findById(String id) {
        return cardRepository.findById(id);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public List<Card> findByCategoryId(List<String> categoryIds, String languageId, int size) {
        List<Card> result = new ArrayList<>();
        int cardsPerCategory = size / categoryIds.size();

        categoryIds.forEach(categoryId -> {
            List<Card> cardsByCategory = cardRepository.findByCategoryIdAndLanguageId(categoryId, languageId);
            result.addAll(cardsByCategory.stream().limit(cardsPerCategory).toList());
        });
        return result;
    }

    public List<Card> findByCategoryIdsAndLevel(List<String> categoryIds, Level level, String languageId, int size) {
        List<Card> result = new ArrayList<>();
        int cardsPerCategory = size / categoryIds.size();

        categoryIds.forEach(categoryId -> {
            List<Card> cardsByCategory = cardRepository.findByCategoryIdAndLevelAndLanguageId(categoryId, level, languageId);
            result.addAll(cardsByCategory.stream().limit(cardsPerCategory).toList());
        });
        return result;
    }

    public List<Card> findCardByLanguageIdAndLevel(String languageId, Level level, int size) {
        return cardRepository.findCardByLanguageIdAndLevel(languageId, level)
                .stream()
                .limit(size)
                .toList();
    }

    public List<Card> findCardByLanguageId(String languageId, int size) {
        return cardRepository.findCardByLanguageId(languageId)
                .stream()
                .limit(size)
                .toList();
    }

    public void deleteById(String id) {
        cardRepository.deleteById(id);
    }
}