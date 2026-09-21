package saber.flow.com.example.demo.application.useCases;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.enums.Level;
import saber.flow.com.example.demo.domain.model.Card;
import saber.flow.com.example.demo.domain.model.UserAnswer;
import saber.flow.com.example.demo.domain.repository.UserAnswerRepository;

@Service
@RequiredArgsConstructor
public class AdaptiveCardUseCase {

    private final CardUseCase cardUseCase;
    private final UserAnswerRepository userAnswerRepository;

    public List<Card> findAdaptive(List<String> categoryIds, String languageId, Level level, int size, String userId) {
        if (size <= 0) {
            return List.of();
        }

        int poolSize = Math.max(size * 3, size);
        List<Card> candidates = resolveCandidates(categoryIds, languageId, level, poolSize);

        if (userId == null || userId.isBlank()) {
            return candidates.stream().limit(size).toList();
        }

        Map<String, List<UserAnswer>> answersByCardId = userAnswerRepository.findByUserId(userId).stream()
                .filter(answer -> answer.getQuestionId() != null && !answer.getQuestionId().isBlank())
                .collect(Collectors.groupingBy(UserAnswer::getQuestionId));

        List<Card> prioritized = candidates.stream()
                .map(card -> new RankedCard(card, evaluatePriority(answersByCardId.getOrDefault(card.getId(), List.of()))))
                .filter(item -> !item.isMastered())
                .sorted(Comparator
                        .comparingInt((RankedCard item) -> item.isRecentIncorrect() ? 0 : 1)
                        .thenComparingInt(RankedCard::getCorrectStreak)
                        .thenComparing(item -> item.card().getId()))
                .map(RankedCard::card)
                .toList();

        if (prioritized.size() >= size) {
            return prioritized.stream().limit(size).toList();
        }

        HashSet<Card> seen = new HashSet<>(prioritized);
        List<Card> completed = candidates.stream()
                .filter(card -> !seen.contains(card))
                .limit(size - prioritized.size())
                .toList();

        return Stream.concat(prioritized.stream(), completed.stream()).limit(size).toList();
    }

    private List<Card> resolveCandidates(List<String> categoryIds, String languageId, Level level, int size) {
        if (categoryIds != null && !categoryIds.isEmpty() && level != null) {
            return cardUseCase.findByCategoryIdsAndLevel(categoryIds, level, languageId, size);
        }

        if ((categoryIds == null || categoryIds.isEmpty()) && level != null) {
            return cardUseCase.findCardByLanguageIdAndLevel(languageId, level, size);
        }

        if (categoryIds != null && !categoryIds.isEmpty() && level == null) {
            return cardUseCase.findByCategoryId(categoryIds, size);
        }

        return cardUseCase.findCardByLanguageId(languageId, size);
    }

    private int evaluatePriority(List<UserAnswer> answers) {
        if (answers == null || answers.isEmpty()) {
            return 0;
        }

        boolean hasIncorrect = answers.stream().anyMatch(answer -> !answer.isCorrect());
        if (hasIncorrect) {
            return 1;
        }

        int correctStreak = getCorrectStreak(answers);
        if (correctStreak >= 3) {
            return 3;
        }

        return 2;
    }

    private int getCorrectStreak(List<UserAnswer> answers) {
        List<UserAnswer> ordered = answers.stream()
                .sorted(Comparator.comparing(UserAnswer::getAnsweredAt).reversed())
                .toList();

        int streak = 0;
        for (UserAnswer answer : ordered) {
            if (!answer.isCorrect()) {
                break;
            }
            streak++;
        }
        return streak;
    }

    private record RankedCard(Card card, int priority) {
        boolean isRecentIncorrect() {
            return priority == 1;
        }

        boolean isMastered() {
            return priority == 3;
        }

        int getCorrectStreak() {
            return priority == 3 ? 3 : 0;
        }
    }
}
