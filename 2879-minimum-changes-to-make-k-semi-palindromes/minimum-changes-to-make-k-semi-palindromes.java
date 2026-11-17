import java.util.Arrays;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1_000_000;
        
        int[][] cost = new int[n][n];
        for (int[] row : cost) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                cost[i][j] = getMinChanges(s, i, j);
            }
        }
        int[][] dp = new int[k + 1][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        
        for (int j = 2; j <= n; j++) {
            dp[1][j] = cost[0][j - 1];
        }
        
        for (int p = 2; p <= k; p++) {
            for (int j = 2 * p; j <= n; j++) {
                for (int i = 2 * (p - 1); i <= j - 2; i++) {
                    if (dp[p - 1][i] != INF && cost[i][j - 1] != INF) {
                        dp[p][j] = Math.min(dp[p][j], dp[p - 1][i] + cost[i][j - 1]);
                    }
                }
            }
        }
        
        return dp[k][n];
    }
    
    private int getMinChanges(String s, int start, int end) {
        int len = end - start + 1;
        int minChanges = Integer.MAX_VALUE;
    
        for (int d = 1; d < len; d++) {
            if (len % d != 0) continue;
            
            int changes = 0;

            for (int rem = 0; rem < d; rem++) {
                int left = start + rem;
                int right = end - (d - 1 - rem);
                while (left < right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        changes++;
                    }
                    left += d;
                    right -= d;
                }
            }
            minChanges = Math.min(minChanges, changes);
        }
        
        return minChanges;
    }
}