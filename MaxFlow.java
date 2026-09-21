import java.io.BufferedReader;
import java.io.IOException;

public class MaxFlow {

    private int vertices;

    public MaxFlow(int vertices) {
        this.vertices = vertices;
    }

    // Finds an available path from source to destination
    private boolean bfs(int[][] residual, int source, int destination, int[] parent) {

        boolean[] visited = new boolean[vertices];
        int[] queue = new int[vertices];

        int front = 0;
        int rear = 0;

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

                    if (next == destination) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Actual Max Flow calculation
    public int findMaxFlow(int[][] graph, int source, int destination) {

        int[][] residual = new int[vertices][vertices];

        // Copy capacities
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                residual[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[vertices];

        int maxFlow = 0;

        // Find paths until no more water can flow
        while (bfs(residual, source, destination, parent)) {

            int pathFlow = Integer.MAX_VALUE;

            // Find the smallest capacity in the path
            for (int v = destination; v != source; v = parent[v]) {

                int u = parent[v];

                pathFlow = Math.min(
                        pathFlow,
                        residual[u][v]
                );
            }

            // Update remaining capacities
            for (int v = destination; v != source; v = parent[v]) {

                int u = parent[v];

                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // Water Distribution part
    public static void runWaterDistribution(BufferedReader br)
            throws IOException {

        System.out.println("\n========== WATER DISTRIBUTION ==========");

        System.out.print("Enter number of locations: ");
        int n = Integer.parseInt(br.readLine().trim());

        System.out.print("Enter source location: ");
        int source = Integer.parseInt(br.readLine().trim());

        System.out.print("Enter destination location: ");
        int destination = Integer.parseInt(br.readLine().trim());

        int[][] graph = new int[n][n];

        // For now, create two automatic paths
        // Source -> Location 1 -> Destination
        // Source -> Location 2 -> Destination

        System.out.print("Enter capacity of path 1 (litres/minute): ");
        int path1 = Integer.parseInt(br.readLine().trim());

        System.out.print("Enter capacity of path 2 (litres/minute): ");
        int path2 = Integer.parseInt(br.readLine().trim());

        graph[source][1] = path1;
        graph[1][destination] = path1;

        graph[source][2] = path2;
        graph[2][destination] = path2;

        // Create MaxFlow object
        MaxFlow maxFlow = new MaxFlow(n);

        // Calculate actual maximum flow
        int result = maxFlow.findMaxFlow(
                graph,
                source,
                destination
        );

        System.out.println(
                "Maximum Flow: "
                + result
                + " litres/minute"
        );
    }
}