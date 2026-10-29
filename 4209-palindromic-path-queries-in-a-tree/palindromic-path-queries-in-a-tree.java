import java.util.*;

class Solution {
    private int timer = 0;
    private int[] in;
    private int[] out;
    private int[][] up;
    private int[] depth;
    private int LOG;
    private int[] nodeMask;

    static class FenwickTree {
        int n;
        int[] tree;

        public FenwickTree(int n) {
            this.n = n;
            this.tree = new int[n + 2];
        }

        public void add(int i, int delta) {
            for (; i <= n; i += i & -i) {
                tree[i] ^= delta;
            }
        }

        public void rangeUpdate(int l, int r, int delta) {
            add(l, delta);
            add(r + 1, delta);
        }

        public int query(int i) {
            int sum = 0;
            for (; i > 0; i -= i & -i) {
                sum ^= tree[i];
            }
            return sum;
        }
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        in = new int[n];
        out = new int[n];
        depth = new int[n];
        up = new int[n][LOG];
        nodeMask = new int[n];
        char[] charArr = s.toCharArray();

        for (int i = 0; i < n; i++) {
            nodeMask[i] = 1 << (charArr[i] - 'a');
        }

        dfs(0, 0, adj, 0);

        FenwickTree bit = new FenwickTree(n);

        int[] pathMask = new int[n];
        initPathMask(0, 0, 0, adj, pathMask);
        for (int i = 0; i < n; i++) {
            bit.rangeUpdate(in[i], in[i], pathMask[i]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char newChar = parts[2].charAt(0);

                int oldBit = nodeMask[u];
                int newBit = 1 << (newChar - 'a');
                int diff = oldBit ^ newBit;

                nodeMask[u] = newBit;

                bit.rangeUpdate(in[u], out[u] - 1, diff);

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int lcaNode = getLCA(u, v);
                int maskU = bit.query(in[u]);
                int maskV = bit.query(in[v]);

                int pathXor = maskU ^ maskV ^ nodeMask[lcaNode];

                ans.add((pathXor & (pathXor - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int p, List<List<Integer>> adj, int d) {
        in[u] = ++timer;
        depth[u] = d;
        up[u][0] = p;
        for (int i = 1; i < LOG; i++) {
            up[u][i] = up[up[u][i - 1]][i - 1];
        }

        for (int v : adj.get(u)) {
            if (v != p) {
                dfs(v, u, adj, d + 1);
            }
        }
        out[u] = timer + 1;
    }

    private void initPathMask(int u, int p, int currentMask, List<List<Integer>> adj, int[] pathMask) {
        currentMask ^= nodeMask[u];
        pathMask[u] = currentMask;
        for (int v : adj.get(u)) {
            if (v != p) {
                initPathMask(v, u, currentMask, adj, pathMask);
            }
        }
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u; u = v; v = temp;
        }

        for (int i = LOG - 1; i >= 0; i--) {
            if (depth[u] - (1 << i) >= depth[v]) {
                u = up[u][i];
            }
        }

        if (u == v) return u;

        for (int i = LOG - 1; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }

        return up[u][0];
    }
}