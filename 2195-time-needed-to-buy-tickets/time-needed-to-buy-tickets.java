class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
        int totalTime = 0;
        int target = tickets[k];

        for (int i = 0; i < tickets.length; i++) {
            if (i <= k) {
                totalTime += Math.min(tickets[i], target);
            } else {
                totalTime += Math.min(tickets[i], target - 1);
            }
        }

        return totalTime;
    }
}