// CO2 - Rabin-Karp with polynomial rolling hash and DOUBLE hashing
public class RabinKarp {
    private static final long BASE = 256;
    private static final long M1 = 1000000007L;
    private static final long M2 = 998244353L;

    public static int[] findAll(String text, String pat) {
        IntList r = new IntList();
        int n = text.length(), m = pat.length();
        if (m == 0 || m > n) return r.toArray();

        long p1 = 1, p2 = 1;                       // BASE^(m-1) under each modulus
        for (int i = 0; i < m - 1; i++) { p1 = p1 * BASE % M1; p2 = p2 * BASE % M2; }

        long hp1 = 0, hp2 = 0, ht1 = 0, ht2 = 0;
        for (int i = 0; i < m; i++) {
            hp1 = (hp1 * BASE + pat.charAt(i)) % M1;
            hp2 = (hp2 * BASE + pat.charAt(i)) % M2;
            ht1 = (ht1 * BASE + text.charAt(i)) % M1;
            ht2 = (ht2 * BASE + text.charAt(i)) % M2;
        }
        for (int i = 0; i + m <= n; i++) {
            if (hp1 == ht1 && hp2 == ht2) {         // verify to rule out collisions
                int j = 0;
                while (j < m && text.charAt(i + j) == pat.charAt(j)) j++;
                if (j == m) r.add(i);
            }
            if (i + m < n) {
                ht1 = ((ht1 - text.charAt(i) * p1 % M1 + M1) % M1 * BASE + text.charAt(i + m)) % M1;
                ht2 = ((ht2 - text.charAt(i) * p2 % M2 + M2) % M2 * BASE + text.charAt(i + m)) % M2;
            }
        }
        return r.toArray();
    }
}
