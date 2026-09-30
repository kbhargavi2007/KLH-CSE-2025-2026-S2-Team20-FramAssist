import java.io.BufferedReader;
import java.io.IOException;

// CO3 - Bitmask DP (Travelling Salesperson): shortest route visiting all fields/markets
public class RoutePlanner {
    public static void run(BufferedReader br) throws IOException {
        System.out.println("\n========== FIELD VISIT ROUTE PLANNER (Bitmask DP / TSP) ==========");
        int n = Util.readInt(br, "Number of locations (location 0 = farm, max 15): ");
        if (n < 2 || n > 15) { System.out.println("Please use between 2 and 15 locations."); return; }
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++) {
            int[] row = Util.readInts(br, "Distances from location " + i + " to 0.." + (n - 1) + ": ");
            for (int j = 0; j < n && j < row.length; j++) d[i][j] = row[j];
        }

        final int INF = 1000000000;
        int FULL = 1 << n;
        int[][] dp = new int[FULL][n];
        int[][] par = new int[FULL][n];
        for (int m = 0; m < FULL; m++) for (int i = 0; i < n; i++) { dp[m][i] = INF; par[m][i] = -1; }
        dp[1][0] = 0;

        for (int mask = 1; mask < FULL; mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0 || dp[mask][i] >= INF) continue;
                for (int j = 0; j < n; j++) {
                    if ((mask & (1 << j)) != 0) continue;
                    int nm = mask | (1 << j);
                    int cost = dp[mask][i] + d[i][j];
                    if (cost < dp[nm][j]) { dp[nm][j] = cost; par[nm][j] = i; }
                }
            }
        }
        int best = INF, last = -1;
        for (int i = 1; i < n; i++) {
            if (dp[FULL - 1][i] < INF && dp[FULL - 1][i] + d[i][0] < best) { best = dp[FULL - 1][i] + d[i][0]; last = i; }
        }
        int[] path = new int[n];
        int mask = FULL - 1, cur = last;
        for (int k = n - 1; k >= 1; k--) { path[k] = cur; int p = par[mask][cur]; mask ^= (1 << cur); cur = p; }
        path[0] = 0;

        StringBuilder sb = new StringBuilder("0");
        for (int k = 1; k < n; k++) sb.append(" -> ").append(path[k]);
        sb.append(" -> 0");
        System.out.println("Shortest round trip: " + sb);
        System.out.println("Total distance: " + best);
    }
}
