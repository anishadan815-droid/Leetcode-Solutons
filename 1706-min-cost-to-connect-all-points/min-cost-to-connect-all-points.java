class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int totalCost = 0;
        int numEdges = 0;

        // minDist[i] will store the minimum distance from node i to the current MST
        int[] minDist = new int[n];
        for (int i = 1; i < n; i++) {
            minDist[i] = Integer.MAX_VALUE;
        }

        boolean[] visited = new boolean[n];

        while (numEdges < n) {
            int currNode = -1;
            int currMinDist = Integer.MAX_VALUE;

            // Pick the unvisited node with the smallest distance to the MST
            for (int i = 0; i < n; i++) {
                if (!visited[i] && minDist[i] < currMinDist) {
                    currMinDist = minDist[i];
                    currNode = i;
                }
            }

            // Include current node in MST
            visited[currNode] = true;
            totalCost += currMinDist;
            numEdges++;

            // Update minDist for adjacent (unvisited) nodes
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    int dist = Math.abs(points[currNode][0] - points[i][0]) 
                             + Math.abs(points[currNode][1] - points[i][1]);
                    
                    if (dist < minDist[i]) {
                        minDist[i] = dist;
                    }
                }
            }
        }

        return totalCost;
    }
}