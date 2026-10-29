package ua.kpi.comsys.collections;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        WordCounter counter = new WordCounter();

        List<String> text = List.of(
                "To be, or not to be: that is the question.",
                "Java 21 is the new LTS; Java is everywhere!",
                ""
        );

        // TreeMap / TreeSet тільки для того, щоб вивід був відсортований
        Map<String, Integer> counts = new TreeMap<>(counter.countWords(text));
        System.out.println("Word counts:");
        counts.forEach((word, n) -> System.out.println("  " + word + " = " + n));

        System.out.println("Unique words: " + new TreeSet<>(counter.getUniqueWords(text)));

        try {
            counter.countWords(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
