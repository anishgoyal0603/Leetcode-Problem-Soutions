class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        // OPTIMIZATION 1: Sort the array to unlock Pruning
        Arrays.sort(candidates); 
        
        // Pass a single, reusable ArrayList to save Heap memory
        backtrack(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int idx, int[] arr, int target, List<Integer> current, List<List<Integer>> result) {
        // Base Case: Target hit. This is the ONLY time we allocate a new list.
        if (target == 0) {
            result.add(new ArrayList<>(current)); 
            return;
        }

        for (int i = idx; i < arr.length; i++) {
            // OPTIMIZATION 2: Pruning. If the current number is bigger than the 
            // remaining target, every number after it will be too. Stop exploring!
            if (arr[i] > target) {
                break; 
            }

            // 1. Choose (O(1) append)
            current.add(arr[i]);
            
            // 2. Explore (Pass 'i' to allow infinite reuse of the same number)
            backtrack(i, arr, target - arr[i], current, result);
            // in this step if we by mistake pass idx then we get redundant answers like if we wanted answer as [[2,2,3],[7]] , then we will get [[2,2,3],[2,3,2],[3,2,2],[7]]
            
            // 3. Un-choose (O(1) removal to clean the scratchpad)
            current.remove(current.size() - 1);
        }
    }
}