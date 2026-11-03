import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        // 0 = unvisited, 1 = color A, -1 = color B
        int[] color = new int[n];

        for (int i = 0; i < n; i++) {
            // Skip already colored nodes
            if (color[i] != 0) continue;

            // Start BFS from node i
            Queue<Integer> queue = new LinkedList<>();
            queue.add(i);
            color[i] = 1; // Start coloring with 1

            while (!queue.isEmpty()) {
                int node = queue.poll();

                for (int neighbor : graph[node]) {
                    if (color[neighbor] == 0) {
                        // Assign the opposite color to the neighbor
                        color[neighbor] = -color[node];
                        queue.add(neighbor);
                    } else if (color[neighbor] == color[node]) {
                        // Conflict found: neighbor has the same color
                        return false;
                    }
                }
            }
        }

        return true;
    }
}