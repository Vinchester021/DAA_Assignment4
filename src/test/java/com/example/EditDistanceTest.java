package com.example;

import com.example.edit.RecursiveEditDistance;
import com.example.edit.TableEditDistance;
import com.example.edit.TwoRowsEditDistance;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditDistanceTest {

    @Test
    void referenceValuesAreCorrect() {

        RecursiveEditDistance recursive =
                new RecursiveEditDistance();

        TableEditDistance table =
                new TableEditDistance();

        TwoRowsEditDistance twoRows =
                new TwoRowsEditDistance();

        checkAll(recursive, table, twoRows,
                "kitten", "sitting", 3);

        checkAll(recursive, table, twoRows,
                "flaw", "lawn", 2);

        checkAll(recursive, table, twoRows,
                "intention", "execution", 5);

        checkAll(recursive, table, twoRows,
                "", "abc", 3);

        checkAll(recursive, table, twoRows,
                "abc", "abc", 0);
    }

    @Test
    void randomShortStringsMatch() {

        Random rnd = new Random(42);

        RecursiveEditDistance recursive =
                new RecursiveEditDistance();

        TableEditDistance table =
                new TableEditDistance();

        TwoRowsEditDistance twoRows =
                new TwoRowsEditDistance();

        for (int test = 0; test < 100; test++) {

            String a = randomString(rnd, 5);
            String b = randomString(rnd, 5);

            int r1 = recursive.distance(a, b);
            int r2 = table.distance(a, b);
            int r3 = twoRows.distance(a, b);

            assertEquals(r1, r2);
            assertEquals(r1, r3);
        }
    }

    private static void checkAll(
            RecursiveEditDistance recursive,
            TableEditDistance table,
            TwoRowsEditDistance twoRows,
            String a,
            String b,
            int expected
    ) {

        assertEquals(expected,
                recursive.distance(a, b));

        assertEquals(expected,
                table.distance(a, b));

        assertEquals(expected,
                twoRows.distance(a, b));
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
                    'a' + rnd.nextInt(4)
            );

            builder.append(c);
        }

        return builder.toString();
    }
}