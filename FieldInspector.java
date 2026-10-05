import java.io.BufferedReader;
import java.io.IOException;

// CO5 - Vertex Cover 2-approximation (maximal matching) vs exact brute force.
// Fields are vertices; an edge = two neighbouring fields that can pass a disease to each other.
// Inspecting a "vertex cover" of fields covers every risky neighbour pair.
public class FieldInspector {
    public static void run(BufferedReader br) throws IOException {
        System.out.println("\n========== FIELD INSPECTION PLANNER (Vertex Cover 2-Approximation) ==========");
        int n = Util.readInt(br, "Number of fields (0..n-1, max 20): ");
        if (n < 1 || n > 20) { System.out.println("Please use 1 to 20 fields."); return; }
        int m = Util.readInt(br, "Number of neighbouring pairs: ");
        int[] a = new int[m], b = new int[m];
        int cnt = 0;
        for (int i = 1; i <= m; i++) {
            int[] e = Util.readInts(br, "Pair " + i + " (two field numbers): ");
            if (e.length >= 2 && e[0] >= 0 && e[0] < n && e[1] >= 0 && e[1] < n) { a[cnt] = e[0]; b[cnt] = e[1]; cnt++; }
        }

        // 2-approximation: take both endpoints of any uncovered edge
        boolean[] chosen = new boolean[n];
        for (int i = 0; i < cnt; i++) {
            if (!chosen[a[i]] && !chosen[b[i]]) { chosen[a[i]] = true; chosen[b[i]] = true; }
        }
        int approx = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) if (chosen[i]) { approx++; sb.append(i).append(' '); }

        // exact optimum by trying every subset (fine for n <= 20)
        int opt = n;
        for (int mask = 0; mask < (1 << n); mask++) {
            int size = Integer.bitCount(mask);
            if (size >= opt) continue;
            boolean ok = true;
            for (int i = 0; i < cnt && ok; i++)
                if ((mask & (1 << a[i])) == 0 && (mask & (1 << b[i])) == 0) ok = false;
            if (ok) opt = size;
        }
        System.out.println("Fields to inspect (approximation): " + sb.toString().trim() + "  [" + approx + " fields]");
        System.out.println("Optimal number of fields (exact):  " + opt);
        if (opt > 0) System.out.println("Approximation ratio: " + (approx * 1.0 / opt) + "  (guaranteed <= 2)");
    }
}
