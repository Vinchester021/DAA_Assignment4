package com.example.benchmark;

import com.example.edit.RecursiveEditDistance;
import com.example.edit.TableEditDistance;
import com.example.edit.TwoRowsEditDistance;
import com.example.search.KMPSearch;
import com.example.search.NaiveSearch;

import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;

public class Benchmark {

    private static final int RUNS = 5;

    public static void main(String[] args) throws Exception {

        new File("results").mkdirs();

        PrintWriter writer =
                new PrintWriter("results/results.csv");

        writer.println(
                "algorithm,input,n,m,run,time_ms,operations,metric"
        );

        runEditDistance(writer);
        runStringSearch(writer);

        writer.close();

        System.out.println(
                "Benchmark finished. Results saved to results/results.csv"
        );
    }

    private static void runEditDistance(
            PrintWriter writer
    ) {

        StringGenerator generator =
                new StringGenerator();

        int[] recursiveSizes =
                {2, 4, 6, 8};

        int[] dpSizes =
                {100, 200, 400, 800};


        // Recursive - equal strings
        for (int n : recursiveSizes) {

            String a =
                    generator.repeatedString('A', n);

            String b =
                    generator.repeatedString('A', n);

            for (int run = 1; run <= RUNS; run++) {

                RecursiveEditDistance algorithm =
                        new RecursiveEditDistance();

                long start = System.nanoTime();

                algorithm.distance(a, b);

                long end = System.nanoTime();

                writeRow(
                        writer,
                        "RecursiveEditDistance",
                        "equal",
                        n,
                        n,
                        run,
                        end - start,
                        algorithm.getCalls(),
                        "recursive_calls"
                );
            }
        }


        // Recursive - different strings
        for (int n : recursiveSizes) {

            String a =
                    generator.repeatedString('A', n);

            String b =
                    generator.repeatedString('B', n);

            for (int run = 1; run <= RUNS; run++) {

                RecursiveEditDistance algorithm =
                        new RecursiveEditDistance();

                long start = System.nanoTime();

                algorithm.distance(a, b);

                long end = System.nanoTime();

                writeRow(
                        writer,
                        "RecursiveEditDistance",
                        "different",
                        n,
                        n,
                        run,
                        end - start,
                        algorithm.getCalls(),
                        "recursive_calls"
                );
            }
        }


        // Table DP
        for (String inputType :
                new String[]{"equal", "different"}) {

            for (int n : dpSizes) {

                String a =
                        generator.repeatedString('A', n);

                String b;

                if (inputType.equals("equal")) {

                    b = generator.repeatedString(
                            'A',
                            n
                    );

                } else {

                    b = generator.repeatedString(
                            'B',
                            n
                    );
                }

                for (int run = 1;
                     run <= RUNS;
                     run++) {

                    TableEditDistance algorithm =
                            new TableEditDistance();

                    long start =
                            System.nanoTime();

                    algorithm.distance(a, b);

                    long end =
                            System.nanoTime();

                    writeRow(
                            writer,
                            "TableEditDistance",
                            inputType,
                            n,
                            n,
                            run,
                            end - start,
                            algorithm.getCellUpdates(),
                            "cell_updates"
                    );
                }
            }
        }


        // Two Rows DP
        for (String inputType :
                new String[]{"equal", "different"}) {

            for (int n : dpSizes) {

                String a =
                        generator.repeatedString('A', n);

                String b;

                if (inputType.equals("equal")) {

                    b = generator.repeatedString(
                            'A',
                            n
                    );

                } else {

                    b = generator.repeatedString(
                            'B',
                            n
                    );
                }

                for (int run = 1;
                     run <= RUNS;
                     run++) {

                    TwoRowsEditDistance algorithm =
                            new TwoRowsEditDistance();

                    long start =
                            System.nanoTime();

                    algorithm.distance(a, b);

                    long end =
                            System.nanoTime();

                    writeRow(
                            writer,
                            "TwoRowsEditDistance",
                            inputType,
                            n,
                            n,
                            run,
                            end - start,
                            algorithm.getCellUpdates(),
                            "cell_updates"
                    );
                }
            }
        }
    }


    private static void runStringSearch(
            PrintWriter writer
    ) {

        StringGenerator generator =
                new StringGenerator();

        int[] sizes =
                {1000, 2000, 4000, 8000};

        for (int n : sizes) {

            int m = n / 4;


            // Random input
            String randomText =
                    generator.randomString(n);

            String randomPattern =
                    generator.randomString(m);

            runNaive(
                    writer,
                    randomText,
                    randomPattern,
                    "random",
                    n,
                    m
            );

            runKMP(
                    writer,
                    randomText,
                    randomPattern,
                    "random",
                    n,
                    m
            );


            // Worst-case input
            String worstText =
                    generator.repeatedString(
                            'A',
                            n
                    );

            String worstPattern =
                    generator.worstCasePattern(m);

            runNaive(
                    writer,
                    worstText,
                    worstPattern,
                    "worst",
                    n,
                    m
            );

            runKMP(
                    writer,
                    worstText,
                    worstPattern,
                    "worst",
                    n,
                    m
            );
        }
    }


    private static void runNaive(
            PrintWriter writer,
            String text,
            String pattern,
            String inputType,
            int n,
            int m
    ) {

        for (int run = 1; run <= RUNS; run++) {

            NaiveSearch algorithm =
                    new NaiveSearch();

            long start =
                    System.nanoTime();

            algorithm.search(text, pattern);

            long end =
                    System.nanoTime();

            writeRow(
                    writer,
                    "NaiveSearch",
                    inputType,
                    n,
                    m,
                    run,
                    end - start,
                    algorithm.getComparisons(),
                    "char_comparisons"
            );
        }
    }


    private static void runKMP(
            PrintWriter writer,
            String text,
            String pattern,
            String inputType,
            int n,
            int m
    ) {

        for (int run = 1; run <= RUNS; run++) {

            KMPSearch algorithm =
                    new KMPSearch();

            long start =
                    System.nanoTime();

            algorithm.search(text, pattern);

            long end =
                    System.nanoTime();

            writeRow(
                    writer,
                    "KMPSearch",
                    inputType,
                    n,
                    m,
                    run,
                    end - start,
                    algorithm.getComparisons(),
                    "char_comparisons"
            );
        }
    }


    private static void writeRow(
            PrintWriter writer,
            String algorithm,
            String input,
            int n,
            int m,
            int run,
            long timeNs,
            long operations,
            String metric
    ) {

        double timeMs =
                timeNs / 1_000_000.0;

        writer.printf(
                Locale.US,
                "%s,%s,%d,%d,%d,%.6f,%d,%s%n",
                algorithm,
                input,
                n,
                m,
                run,
                timeMs,
                operations,
                metric
        );
    }
}