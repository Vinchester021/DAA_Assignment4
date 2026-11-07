package com.example.benchmark;

import java.util.Random;

public class StringGenerator {

    private final Random random;

    public StringGenerator() {
        random = new Random(42);
    }

    public String randomString(int length) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {

            char c = (char) (
                    'A' + random.nextInt(4)
            );

            builder.append(c);
        }

        return builder.toString();
    }

    public String repeatedString(
            char character,
            int length
    ) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(character);
        }

        return builder.toString();
    }

    public String worstCasePattern(int length) {

        if (length <= 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length - 1; i++) {
            builder.append('A');
        }

        builder.append('B');

        return builder.toString();
    }
}