// CO2 - Z-function pattern matching, O(n + m)
public class ZAlgorithm {

    public static int[] calculateZ(String text) {
        int n = text.length();
        int[] z = new int[n];
        int left = 0, right = 0;
        for (int i = 1; i < n; i++) {
            if (i <= right) z[i] = Math.min(right - i + 1, z[i - left]);
            while (i + z[i] < n && text.charAt(z[i]) == text.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > right) { left = i; right = i + z[i] - 1; }
        }
        return z;
    }

    // Returns every start position of pattern in text
    public static int[] findAll(String text, String pattern) {
        IntList r = new IntList();
        if (pattern.length() == 0) return r.toArray();
        int[] z = calculateZ(pattern + "\u0001" + text);
        for (int i = pattern.length() + 1; i < z.length; i++) {
            if (z[i] >= pattern.length()) r.add(i - pattern.length() - 1);
        }
        return r.toArray();
    }

    // Kept from the original version
    public static void search(String text, String pattern) {
        int[] pos = findAll(text, pattern);
        for (int p : pos) System.out.println("Match found at position: " + p);
        System.out.println("Total matches: " + pos.length);
    }
}
