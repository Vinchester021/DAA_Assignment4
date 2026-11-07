package com.example.daa.edit;

public class RecursiveEditDistance {

    private long calls;

    public int distance(String a, String b) {
        calls = 0;
        return solve(a, b, a.length(), b.length());
    }

    private int solve(String a, String b, int n, int m) {

        calls++;

        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        if (a.charAt(n - 1) == b.charAt(m - 1)) {
            return solve(a, b, n - 1, m - 1);
        }

        int insert = solve(a, b, n, m - 1);
        int delete = solve(a, b, n - 1, m);
        int replace = solve(a, b, n - 1, m - 1);

        return 1 + Math.min(
                insert,
                Math.min(delete, replace)
        );
    }

    public long getCalls() {
        return calls;
    }
}