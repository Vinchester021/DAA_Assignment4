package com.example.edit;

public class TwoRowsEditDistance {

    private long cellUpdates;

    public int distance(String a, String b) {

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        cellUpdates = 0;

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
            cellUpdates++;
        }


        for (int i = 1; i <= n; i++) {

            current[0] = i;
            cellUpdates++;

            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    current[j] =
                            previous[j - 1];

                } else {

                    int insert =
                            current[j - 1];

                    int delete =
                            previous[j];

                    int replace =
                            previous[j - 1];

                    current[j] =
                            1 + Math.min(
                                    insert,
                                    Math.min(delete, replace)
                            );
                }

                cellUpdates++;
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }

    public long getCellUpdates() {
        return cellUpdates;
    }
}