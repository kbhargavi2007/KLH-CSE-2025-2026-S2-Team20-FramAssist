// CO1 - Algorithm selection: compare Naive, KMP, Z, Rabin-Karp on the real corpus
public class Benchmark {
    public static void run(KnowledgeBase kb, String pattern) {
        String text = kb.corpusLower;
        String p = pattern.toLowerCase();
        System.out.println("\n========== STRING SEARCH BENCHMARK ==========");
        System.out.println("Corpus size: " + text.length() + " chars, pattern: \"" + p + "\"\n");
        System.out.println(pad("Algorithm", 14) + pad("Matches", 10) + pad("Time (us)", 12) + pad("Comparisons", 13) + "Complexity");

        NaiveSearch.comparisons = 0;
        long t = System.nanoTime();
        int a = NaiveSearch.findAll(text, p).length;
        row("Naive", a, System.nanoTime() - t, NaiveSearch.comparisons + "", "O(n*m)");

        KMP.comparisons = 0;
        t = System.nanoTime();
        a = KMP.findAll(text, p).length;
        row("KMP", a, System.nanoTime() - t, KMP.comparisons + "", "O(n+m)");

        t = System.nanoTime();
        a = ZAlgorithm.findAll(text, p).length;
        row("Z-Algorithm", a, System.nanoTime() - t, "-", "O(n+m)");

        t = System.nanoTime();
        a = RabinKarp.findAll(text, p).length;
        row("Rabin-Karp", a, System.nanoTime() - t, "-", "O(n+m) avg");
        System.out.println("\nAll four must report the same match count (correctness check).");
    }

    private static void row(String name, int matches, long ns, String cmp, String cx) {
        System.out.println(pad(name, 14) + pad(matches + "", 10) + pad((ns / 1000) + "", 12) + pad(cmp, 13) + cx);
    }

    private static String pad(String s, int w) {
        while (s.length() < w) s += " ";
        return s;
    }
}
