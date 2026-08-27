class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // Handle patterns matching empty string (e.g., "a*", "a*b*")
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                
                if (pChar == '*') {
                    // Match zero occurrences of preceding element
                    dp[i][j] = dp[i][j - 2];
                    
                    // Match one or more occurrences if preceding char matches s[i-1]
                    char prevChar = p.charAt(j - 2);
                    if (prevChar == '.' || prevChar == s.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pChar == '.' || pChar == s.charAt(i - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        
        return dp[m][n];
    }
}