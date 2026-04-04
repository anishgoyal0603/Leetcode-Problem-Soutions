class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        solve(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void solve(int index, int[] arr, int target, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(current); 
            return;
        }
        if (target < 0 || index == arr.length) return;

        // BAD: Creating brand new objects in memory at every single step
        List<Integer> pickList = new ArrayList<>(current); 
        pickList.add(arr[index]);
        solve(index, arr, target - arr[index], pickList, result);

        List<Integer> noPickList = new ArrayList<>(current); 
        solve(index + 1, arr, target, noPickList, result);
    }
}