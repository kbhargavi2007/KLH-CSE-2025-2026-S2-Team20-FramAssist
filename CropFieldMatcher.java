import java.io.BufferedReader;
import java.io.IOException;

// CO4 - Bipartite matching solved as a max-flow problem: assign crops to fields
public class CropFieldMatcher {
    public static void run(BufferedReader br, KnowledgeBase kb) throws IOException {
        System.out.println("\n========== CROP -> FIELD ASSIGNMENT (Bipartite Matching) ==========");
        int L = Math.min(kb.count, 8);
        if (L == 0) { System.out.println("No crops loaded."); return; }
        int F = Util.readInt(br, "Number of fields available: ");
        if (F <= 0) return;

        int V = L + F + 2, S = 0, T = V - 1;
        int[][] g = new int[V][V];
        for (int i = 0; i < L; i++) {
            g[S][1 + i] = 1;
            int[] ok = Util.readInts(br, "Fields (1.." + F + ") suitable for " + kb.records[i].name + " (space separated): ");
            for (int f : ok) if (f >= 1 && f <= F) g[1 + i][1 + L + (f - 1)] = 1;
        }
        for (int j = 0; j < F; j++) g[1 + L + j][T] = 1;

        MaxFlow mf = new MaxFlow(V);
        int matched = mf.findMaxFlow(g, S, T);
        int[][] res = mf.getResidual();

        System.out.println("\nCrops that can be planted at the same time: " + matched);
        for (int i = 0; i < L; i++) {
            String field = "not assigned";
            for (int j = 0; j < F; j++)
                if (g[1 + i][1 + L + j] == 1 && res[1 + i][1 + L + j] == 0) field = "Field " + (j + 1);
            System.out.println("  " + kb.records[i].name + " -> " + field);
        }
    }
}
