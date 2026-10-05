import java.io.BufferedReader;
import java.io.IOException;

// Universal search: crop name, disease, pest, soil, fertilizer... anything in the corpus.
// Uses Z-algorithm (exact crop name), KMP (search inside every field), Edit Distance (fuzzy).
public class FileSearcher {

    public static void search(KnowledgeBase kb, String query, BufferedReader br) throws IOException {
        String q = query.trim().toLowerCase();
        System.out.println("\n========== SEARCH RESULTS ==========");
        if (q.isEmpty()) { System.out.println("Please type something to search."); return; }

        // 1. Exact crop-name match (Z algorithm) -> show full details
        for (int r = 0; r < kb.count; r++) {
            String name = kb.records[r].name.toLowerCase();
            if (name.length() == q.length() && ZAlgorithm.findAll(name, q).length > 0) {
                printDetails(kb.records[r]);
                return;
            }
        }

        // 2. Search every field of every crop (KMP)
        int[] hits = printHits(kb, q);

        // 3. Nothing found -> fuzzy match with edit distance
        if (hits.length == 0) {
            String s = suggest(kb, q);
            if (s == null) { System.out.println("No related crop, disease or keyword found for \"" + query + "\"."); return; }
            System.out.println("No exact match for \"" + query + "\". Did you mean: \"" + s + "\"?");
            for (int r = 0; r < kb.count; r++) {
                if (kb.records[r].name.equalsIgnoreCase(s)) { printDetails(kb.records[r]); return; }
            }
            hits = printHits(kb, s.toLowerCase());
        }

        if (hits.length > 0) {
            int pick = Util.readInt(br, "\nEnter result number for full crop details (0 to go back): ");
            if (pick >= 1 && pick <= hits.length) printDetails(kb.records[hits[pick - 1]]);
        }
    }

    // ---- prints every crop whose fields contain the term; returns record indexes
    private static int[] printHits(KnowledgeBase kb, String q) {
        IntList hits = new IntList();
        for (int r = 0; r < kb.count; r++) {
            CropRecord rec = kb.records[r];
            for (int f = 0; f < rec.fieldCount; f++) {
                if (containsWord(rec.values[f].toLowerCase(), q)) { hits.add(r); break; }
            }
        }
        if (hits.size() == 0) return hits.toArray();

        System.out.println("\"" + q + "\" found in " + hits.size() + " crop(s):");
        for (int h = 0; h < hits.size(); h++) {
            CropRecord rec = kb.records[hits.get(h)];
            System.out.println("\n " + (h + 1) + ") " + rec.name);
            for (int f = 0; f < rec.fieldCount; f++) {
                if (KnowledgeBase.isNameKey(rec.keys[f])) continue;
                if (containsWord(rec.values[f].toLowerCase(), q)) {
                    System.out.println("      - " + rec.keys[f] + ": " + rec.values[f]);
                }
            }
        }
        return hits.toArray();
    }

    // KMP match that must start and end on a word boundary
    private static boolean containsWord(String text, String q) {
        int[] pos = KMP.findAll(text, q);
        for (int i = 0; i < pos.length; i++) {
            int s = pos[i], e = s + q.length();
            boolean left = s == 0 || !isWordChar(text.charAt(s - 1));
            boolean right = e == text.length() || !isWordChar(text.charAt(e));
            if (left && right) return true;
        }
        return false;
    }

    private static boolean isWordChar(char c) { return Character.isLetterOrDigit(c); }

    // ---- fuzzy suggestion: closest crop name / word in the corpus (Edit Distance)
    private static String suggest(KnowledgeBase kb, String q) {
        if (q.length() < 3) return null;
        int limit = q.length() <= 4 ? 1 : 2;
        String best = null;
        int bestD = Integer.MAX_VALUE;

        for (int r = 0; r < kb.count; r++) {
            CropRecord rec = kb.records[r];
            // whole crop name (handles multi-word names)
            int d = distanceIfClose(q, rec.name.toLowerCase(), limit);
            if (d >= 0 && d < bestD) { bestD = d; best = rec.name; }
            // every word in every field
            for (int f = 0; f < rec.fieldCount; f++) {
                String v = rec.values[f];
                int i = 0;
                while (i < v.length()) {
                    while (i < v.length() && !isWordChar(v.charAt(i))) i++;
                    int s = i;
                    while (i < v.length() && isWordChar(v.charAt(i))) i++;
                    if (i > s) {
                        String w = v.substring(s, i);
                        int dw = distanceIfClose(q, w.toLowerCase(), limit);
                        if (dw >= 0 && dw < bestD) { bestD = dw; best = w; }
                    }
                }
            }
        }
        return best;
    }

    private static int distanceIfClose(String a, String b, int limit) {
        if (Math.abs(a.length() - b.length()) > limit || b.length() < 3) return -1;
        int d = EditDistance.calculate(a, b);
        return d <= limit ? d : -1;
    }

    private static void printDetails(CropRecord rec) {
        System.out.println("\n========== CROP DETAILS ==========");
        for (int f = 0; f < rec.fieldCount; f++) {
            System.out.println(rec.keys[f] + ": " + rec.values[f]);
        }
        System.out.println("==================================");
    }
}
