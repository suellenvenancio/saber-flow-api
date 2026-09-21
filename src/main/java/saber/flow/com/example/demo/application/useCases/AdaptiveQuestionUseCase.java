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
import saber.flow.com.example.demo.domain.model.Question;
import saber.flow.com.example.demo.domain.model.UserAnswer;
import saber.flow.com.example.demo.domain.repository.UserAnswerRepository;

@Service
@RequiredArgsConstructor
public class AdaptiveQuestionUseCase {

    private final QuestionUseCase questionUseCase;
    private final UserAnswerRepository userAnswerRepository;

    public List<Question> findAdaptive(List<String> categoryIds, String languageId, Level level, int size, String userId) { 
        int poolSize = Math.max(size * 3, size);
        List<Question> candidates = resolveCandidates(categoryIds, languageId, level, poolSize);

        if (userId == null || userId.isBlank()) {
            return candidates.stream().limit(size).toList();
        }

        Map<String, List<UserAnswer>> answersByQuestionId = userAnswerRepository.findByUserId(userId).stream()
                .filter(answer -> answer.getQuestionId() != null && !answer.getQuestionId().isBlank())
                .collect(Collectors.groupingBy(UserAnswer::getQuestionId));

        List<Question> prioritized = candidates.stream()
                .map(question -> new RankedQuestion(question, evaluatePriority(answersByQuestionId.getOrDefault(question.getId(), List.of()))))
                .filter(item -> !item.isMastered())
                .sorted(Comparator
                        .comparingInt((RankedQuestion item) -> item.isRecentIncorrect() ? 0 : 1)
                        .thenComparingInt(RankedQuestion::getCorrectStreak)
                        .thenComparing(item -> item.question().getId()))
                .map(RankedQuestion::question)
                .toList();

        if (prioritized.size() >= size) {
            return prioritized.stream().limit(size).toList();
        }

        HashSet<Question> seen = new HashSet<>(prioritized);
        List<Question> completed = candidates.stream()
                .filter(question -> !seen.contains(question))
                .limit(size - prioritized.size())
                .toList();

        return Stream.concat(prioritized.stream(), completed.stream()).limit(size).toList();
    }

    private List<Question> resolveCandidates(List<String> categoryIds, String languageId, Level level, int size) {
        if (categoryIds != null && !categoryIds.isEmpty() && level != null) {
            return questionUseCase.findByCategoryIdsAndLevel(categoryIds, level, size);
        }

        if ((categoryIds == null || categoryIds.isEmpty()) && level != null) {
            return questionUseCase.findCardByLanguageIdAndLevel(level, languageId, size);
        }

        if (categoryIds != null && !categoryIds.isEmpty() && level == null) {
            return questionUseCase.findByCategoryIds(categoryIds, size);
        }

        return questionUseCase.findCardByLanguageId(languageId, size);
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

    private record RankedQuestion(Question question, int priority) {
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
