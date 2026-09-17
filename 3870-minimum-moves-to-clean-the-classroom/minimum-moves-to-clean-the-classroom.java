class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startR = -1, startC = -1;
        int litterCount = 0;
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) Arrays.fill(row, -1);

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litterId[r][c] = litterCount++;
                }
            }
        }

        if (litterCount == 0) return 0;
        int targetMask = (1 << litterCount) - 1;

        int[][][] dist = new int[m][n][1 << litterCount];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], -1);
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, 0, energy});
        dist[startR][startC][0] = energy;

        int moves = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1], mask = curr[2], e = curr[3];

                if (mask == targetMask) {
                    return moves;
                }

                for (int[] dir : dirs) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n || classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    int nextEnergy = e - 1;
                    if (nextEnergy < 0) continue;

                    char cellChar = classroom[nr].charAt(nc);
                    int nextMask = mask;

                    if (cellChar == 'R') {
                        nextEnergy = energy;
                    } else if (cellChar == 'L') {
                        nextMask |= (1 << litterId[nr][nc]);
                    }
                    if (nextEnergy > dist[nr][nc][nextMask]) {
                        dist[nr][nc][nextMask] = nextEnergy;
                        queue.offer(new int[]{nr, nc, nextMask, nextEnergy});
                    }
                }
            }
            moves++;
        }

        return -1;
    }
}