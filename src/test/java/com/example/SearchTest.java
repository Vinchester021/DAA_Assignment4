package com.example;

import com.example.search.KMPSearch;
import com.example.search.NaiveSearch;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void lpsReferenceValuesAreCorrect() {

        KMPSearch kmp = new KMPSearch();

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void searchReferenceValuesAreCorrect() {

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        checkBoth(
                naive,
                kmp,
                "ABABDABACDABABCABAB",
                "ABABCABAB",
                List.of(10)
        );

        checkBoth(
                naive,
                kmp,
                "AAAAA",
                "AA",
                List.of(0, 1, 2, 3)
        );

        checkBoth(
                naive,
                kmp,
                "ABABABA",
                "ABA",
                List.of(0, 2, 4)
        );
    }

    @Test
    void randomTextsMatch() {

        Random rnd = new Random(42);

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        for (int test = 0; test < 100; test++) {

            String text = randomString(rnd, 30);
            String pattern = randomString(rnd, 5);

            if (pattern.isEmpty()) {
                pattern = "a";
            }

            assertEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
        }
    }

    @Test
    void emptyPatternThrowsException() {

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        assertThrows(
                IllegalArgumentException.class,
                () -> naive.search("abc", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> kmp.search("abc", "")
        );
    }

    @Test
    void longerPatternReturnsEmptyList() {

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        assertTrue(
                naive.search("abc", "abcdef").isEmpty()
        );

        assertTrue(
                kmp.search("abc", "abcdef").isEmpty()
        );
    }

    private static void checkBoth(
            NaiveSearch naive,
            KMPSearch kmp,
            String text,
            String pattern,
            List<Integer> expected
    ) {

        assertEquals(
                expected,
                naive.search(text, pattern)
        );

        assertEquals(
                expected,
                kmp.search(text, pattern)
        );
    }

    private static String randomString(
            Random rnd,
            int maxLength
    ) {

        int length =
                rnd.nextInt(maxLength + 1);

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0; i < length; i++) {

            char c = (char) (
                    'A' + rnd.nextInt(4)
            );

            builder.append(c);
        }

        return builder.toString();
    }
}