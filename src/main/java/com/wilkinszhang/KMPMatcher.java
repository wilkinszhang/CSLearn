package com.wilkinszhang;

public class KMPMatcher {
    public static void main(String[] args) {
        String text = "ghgghghghghh";
        String pattern = "ghghh";
        KMPMatcher kmp = new KMPMatcher();
        double efficiency = kmp.KMPSearch(pattern, text);
        System.out.println("Match efficiency: " + efficiency);
    }

    public double KMPSearch(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        int[] lps = new int[M];
        int j = 0; // length of previous longest prefix suffix

        // Preprocess the pattern (calculate lps array)
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        int comparisons = 0; // count comparisons

        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            comparisons++;

            if (j == M) {
                System.out.println("Found pattern at index " + (i - j));
                j = lps[j - 1];
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }

        // Calculating match efficiency
        return (double) comparisons / N;
    }

    private void computeLPSArray(String pat, int M, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }
}
