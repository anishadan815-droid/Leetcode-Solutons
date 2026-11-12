class NumArray {
    private int[] nums;
    private int[] tree;
    private int n;

    public NumArray(int[] nums) {
        this.n = nums.length;
        this.nums = new int[n];
        this.tree = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }
    }
    
    public void update(int index, int val) {
        int diff = val - nums[index];
        nums[index] = val;
        
        // Fenwick tree is 1-indexed
        for (int i = index + 1; i <= n; i += (i & -i)) {
            tree[i] += diff;
        }
    }
    
    public int sumRange(int left, int right) {
        return query(right + 1) - query(left);
    }
    private int query(int index) {
        int sum = 0;
        for (int i = index; i > 0; i -= (i & -i)) {
            sum += tree[i];
        }
        return sum;
    }
}