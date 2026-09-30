// CO1 - Naive pattern matching, O(n*m)
public class NaiveSearch {
    public static long comparisons = 0;

    public static int[] findAll(String text, String pat) {
        IntList r = new IntList();
        int n = text.length(), m = pat.length();
        if (m == 0) return r.toArray();
        for (int i = 0; i + m <= n; i++) {
            int j = 0;
            while (j < m) {
                comparisons++;
                if (text.charAt(i + j) != pat.charAt(j)) break;
                j++;
            }
            if (j == m) r.add(i);
        }
        return r.toArray();
    }
}
