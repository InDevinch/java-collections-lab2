package ua.kpi.comsys.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordCounterTest {

    private WordCounter counter;

    @BeforeEach
    void setUp() {
        counter = new WordCounter();
    }

    @Nested
    class CountWords {

        @Test
        void nullListThrowsException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> counter.countWords(null));
            assertEquals("textLines cannot be null", ex.getMessage());
        }

        @Test
        void emptyListReturnsEmptyMap() {
            assertTrue(counter.countWords(List.of()).isEmpty());
        }

        @Test
        void countsSimpleSentence() {
            Map<String, Integer> result = counter.countWords(List.of("to be or not to be"));

            assertEquals(Map.of("to", 2, "be", 2, "or", 1, "not", 1), result);
        }

        @Test
        void ignoresCase() {
            Map<String, Integer> result = counter.countWords(List.of("Java JAVA java"));

            assertEquals(Map.of("java", 3), result);
        }

        @Test
        void punctuationIsSeparator() {
            Map<String, Integer> result = counter.countWords(List.of("Hello, world! Hello... world?!"));

            assertEquals(Map.of("hello", 2, "world", 2), result);
        }

        @Test
        void digitsArePartOfWords() {
            Map<String, Integer> result = counter.countWords(List.of("java21 is java 21"));

            assertEquals(Map.of("java21", 1, "is", 1, "java", 1, "21", 1), result);
        }

        @Test
        void countsAcrossMultipleLines() {
            Map<String, Integer> result = counter.countWords(List.of("one two", "two three", "three three"));

            assertEquals(Map.of("one", 1, "two", 2, "three", 3), result);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "!!!", ",.;:-", "\t\n"})
        void linesWithoutWordsGiveEmptyMap(String line) {
            assertTrue(counter.countWords(List.of(line)).isEmpty());
        }

        @Test
        void leadingSeparatorsDoNotProduceEmptyWords() {
            Map<String, Integer> result = counter.countWords(List.of("  ...start and end...  "));

            assertEquals(Map.of("start", 1, "and", 1, "end", 1), result);
        }

        @Test
        void nonLatinLettersAreSeparators() {
            // кирилиця не входить у латиницю, тому працює як роздільник
            Map<String, Integer> result = counter.countWords(List.of("hello світ world"));

            assertEquals(Map.of("hello", 1, "world", 1), result);
        }

        @Test
        void nullLineInsideListIsSkipped() {
            List<String> lines = Arrays.asList("a b", null, "b");

            assertEquals(Map.of("a", 1, "b", 2), counter.countWords(lines));
        }
    }

    @Nested
    class GetUniqueWords {

        @Test
        void nullListThrowsException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> counter.getUniqueWords(null));
            assertEquals("textLines cannot be null", ex.getMessage());
        }

        @Test
        void emptyListReturnsEmptySet() {
            assertTrue(counter.getUniqueWords(List.of()).isEmpty());
        }

        @Test
        void returnsUniqueLowercaseWords() {
            Set<String> result = counter.getUniqueWords(List.of("The cat and THE dog", "the Cat"));

            assertEquals(Set.of("the", "cat", "and", "dog"), result);
        }

        @Test
        void uniqueWordsMatchKeysOfCountWords() {
            List<String> text = List.of("Lorem ipsum dolor sit amet, lorem IPSUM!", "Sit 42 amet");

            assertEquals(counter.countWords(text).keySet(), counter.getUniqueWords(text));
        }

        @Test
        void nullLineInsideListIsSkipped() {
            List<String> lines = Arrays.asList(null, "x y x");

            assertEquals(Set.of("x", "y"), counter.getUniqueWords(lines));
        }
    }
}
