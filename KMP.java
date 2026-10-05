// CO2 - Knuth-Morris-Pratt, O(n + m)
public class KMP {
    public static long comparisons = 0;

    public static int[] buildLPS(String p) {
        int[] lps = new int[p.length()];
        int len = 0, i = 1;
        while (i < p.length()) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    public static int[] findAll(String text, String pat) {
        IntList r = new IntList();
        int n = text.length(), m = pat.length();
        if (m == 0) return r.toArray();
        int[] lps = buildLPS(pat);
        int i = 0, j = 0;
        while (i < n) {
            comparisons++;
            if (text.charAt(i) == pat.charAt(j)) {
                i++; j++;
                if (j == m) { r.add(i - j); j = lps[j - 1]; }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return r.toArray();
    }
}
