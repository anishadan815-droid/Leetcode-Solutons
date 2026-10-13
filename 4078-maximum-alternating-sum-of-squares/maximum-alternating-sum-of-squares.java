class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];
        
        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }
        
        Arrays.sort(squares);
        
        int subtractCount = n / 2;
        long maxScore = 0;
        
        // Smallest n / 2 elements are subtracted
        for (int i = 0; i < subtractCount; i++) {
            maxScore -= squares[i];
        }
        
        // Remaining larger elements are added
        for (int i = subtractCount; i < n; i++) {
            maxScore += squares[i];
        }
        
        return maxScore;
    }
}