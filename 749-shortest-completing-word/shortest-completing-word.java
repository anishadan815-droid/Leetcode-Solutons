class Solution {
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] targetCounts = getCharCounts(licensePlate);
        String result = null;

        for (String word : words) {
            // If we already have a shorter candidate, skip longer words
            if (result != null && word.length() >= result.length()) {
                continue;
            }

            if (matches(targetCounts, getCharCounts(word))) {
                result = word;
            }
        }

        return result;
    }

    private int[] getCharCounts(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                counts[Character.toLowerCase(c) - 'a']++;
            }
        }
        return counts;
    }

    private boolean matches(int[] target, int[] current) {
        for (int i = 0; i < 26; i++) {
            if (current[i] < target[i]) {
                return false;
            }
        }
        return true;
    }
}