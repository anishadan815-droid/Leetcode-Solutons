class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] best = new int[n];
        Arrays.fill(best, Integer.MAX_VALUE);

        int left = 0;
        int currentSum = 0;
        int minTotalLength = Integer.MAX_VALUE;
        int minLenSoFar = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            currentSum += arr[right];

            while (currentSum > target && left <= right) {
                currentSum -= arr[left];
                left++;
            }

            if (currentSum == target) {
                int currentLen = right - left + 1;
                if (left > 0 && best[left - 1] != Integer.MAX_VALUE) {
                    minTotalLength = Math.min(minTotalLength, best[left - 1] + currentLen);
                }

                minLenSoFar = Math.min(minLenSoFar, currentLen);
            }

            best[right] = minLenSoFar;
        }

        return minTotalLength == Integer.MAX_VALUE ? -1 : minTotalLength;
    }
}