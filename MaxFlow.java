import java.io.BufferedReader;
import java.io.IOException;

// CO4 - Edmonds-Karp (Ford-Fulkerson with BFS)
public class MaxFlow {

    private int vertices;
    private int[][] lastResidual;

    public MaxFlow(int vertices) { this.vertices = vertices; }

    public int[][] getResidual() { return lastResidual; }

    private boolean bfs(int[][] residual, int source, int destination, int[] parent) {
        boolean[] visited = new boolean[vertices];
        int[] queue = new int[vertices];
        int front = 0, rear = 0;
        queue[rear++] = source;
        visited[source] = true;
        parent[source] = -1;
        while (front < rear) {
            int current = queue[front++];
            for (int next = 0; next < vertices; next++) {
                if (!visited[next] && residual[current][next] > 0) {
                    queue[rear++] = next;
                    parent[next] = current;
                    visited[next] = true;
                    if (next == destination) return true;
                }
            }
        }
        return false;
    }

    public int findMaxFlow(int[][] graph, int source, int destination) {
        int[][] residual = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++) residual[i][j] = graph[i][j];

        int[] parent = new int[vertices];
        int maxFlow = 0;
        while (bfs(residual, source, destination, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = destination; v != source; v = parent[v])
                pathFlow = Math.min(pathFlow, residual[parent[v]][v]);
            for (int v = destination; v != source; v = parent[v]) {
                residual[parent[v]][v] -= pathFlow;
                residual[v][parent[v]] += pathFlow;
            }
            maxFlow += pathFlow;
        }
        lastResidual = residual;
        return maxFlow;
    }

    // Water distribution: any network of pipes
    public static void runWaterDistribution(BufferedReader br) throws IOException {
        System.out.println("\n========== WATER DISTRIBUTION (Max Flow) ==========");
        int n = Util.readInt(br, "Number of locations (numbered 0..n-1): ");
        int source = Util.readInt(br, "Source location (e.g. reservoir): ");
        int dest = Util.readInt(br, "Destination location (e.g. field): ");
        int pipes = Util.readInt(br, "Number of pipes: ");
        if (source < 0 || source >= n || dest < 0 || dest >= n) { System.out.println("Invalid locations."); return; }

        int[][] graph = new int[n][n];
        for (int i = 1; i <= pipes; i++) {
            int[] e = Util.readInts(br, "Pipe " + i + " -> from to capacity(litres/min): ");
            if (e.length < 3 || e[0] < 0 || e[0] >= n || e[1] < 0 || e[1] >= n) { System.out.println("  Skipped (bad input)."); continue; }
            graph[e[0]][e[1]] += e[2];
        }
        int result = new MaxFlow(n).findMaxFlow(graph, source, dest);
        System.out.println("Maximum Flow: " + result + " litres/minute");
    }
}
