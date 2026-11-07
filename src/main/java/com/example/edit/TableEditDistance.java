package com.example.edit;

public class TableEditDistance {

    private long cellUpdates;

    public int distance(String a, String b) {

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        cellUpdates = 0;

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
            cellUpdates++;
        }

        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
            cellUpdates++;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    int insert =
                            dp[i][j - 1];

                    int delete =
                            dp[i - 1][j];

                    int replace =
                            dp[i - 1][j - 1];

                    dp[i][j] =
                            1 + Math.min(
                                    insert,
                                    Math.min(delete, replace)
                            );
                }

                cellUpdates++;
            }
        }

        return dp[n][m];
    }

    public long getCellUpdates() {
        return cellUpdates;
    }
}