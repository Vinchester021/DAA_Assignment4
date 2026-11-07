package com.example.search;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {

    private long comparisons;

    public List<Integer> search(String text, String pattern) {

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty"
            );
        }

        List<Integer> matches = new ArrayList<>();

        comparisons = 0;

        if (pattern.length() > text.length()) {
            return matches;
        }

        for (int i = 0;
             i <= text.length() - pattern.length();
             i++) {

            boolean match = true;

            for (int j = 0;
                 j < pattern.length();
                 j++) {

                comparisons++;

                if (text.charAt(i + j)
                        != pattern.charAt(j)) {

                    match = false;
                    break;
                }
            }

            if (match) {
                matches.add(i);
            }
        }

        return matches;
    }

    public long getComparisons() {
        return comparisons;
    }
}