package ua.kpi.comsys.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Частотний аналізатор тексту.
 * Слово - послідовність латинських букв і цифр, регістр не враховується.
 */
public class WordCounter {

    // все, що не латинська буква і не цифра, - роздільник
    private static final Pattern SEPARATOR = Pattern.compile("[^a-zA-Z0-9]+");

    public Map<String, Integer> countWords(List<String> textLines) {
        if (textLines == null) {
            throw new IllegalArgumentException("textLines cannot be null");
        }

        Map<String, Integer> counts = new HashMap<>();
        for (String line : textLines) {
            for (String word : splitToWords(line)) {
                counts.merge(word, 1, Integer::sum);
            }
        }
        return counts;
    }

    public Set<String> getUniqueWords(List<String> textLines) {
        if (textLines == null) {
            throw new IllegalArgumentException("textLines cannot be null");
        }

        Set<String> unique = new HashSet<>();
        for (String line : textLines) {
            unique.addAll(splitToWords(line));
        }
        return unique;
    }

    private List<String> splitToWords(String line) {
        if (line == null) {
            return List.of();
        }
        return SEPARATOR.splitAsStream(line)
                .filter(w -> !w.isEmpty())
                .map(w -> w.toLowerCase(Locale.ROOT))
                .toList();
    }
}
