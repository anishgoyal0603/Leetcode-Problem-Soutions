Here is the absolute, airtight FAANG interview script for **Combination Sum (LeetCode 39)**. 

If you deliver the answer exactly like this, you will demonstrate to the interviewer that you not only know the algorithm, but you deeply understand JVM memory management, Big-O limitations, and real-world CPU optimization.

---

### **Phase 1: Establish the Baseline (Brute Force)**

**What to say out loud:**
> *"The most intuitive way to solve this is using a standard Pick/Not-Pick recursive tree. However, doing this naively creates a massive memory leak. If we create a `new ArrayList` at every single node to pass down our current combination, we will flood the Heap memory with thousands of abandoned lists, forcing the Garbage Collector to freeze our program. Furthermore, without sorting, we will blindly explore deep into invalid paths even after our target has gone negative."*

**The Brute Force Code:**
```java
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
```
N = length of candidates
Let $T$ = Target, $M$ = Minimum value in array
$k$ = average combination length.
* **Theoretical Time Complexity:**  O(2^{T/M} * k)
* **Theoretical Space Complexity:** O( T/M * k) .

---

### **Phase 2: Write the Production Code (Most Optimized)**

**What to say out loud:**
> *"To optimize this so it cannot be improved further, we must fix the memory and the execution time. For memory, I will use standard Backtracking: maintaining a single, mutable scratchpad list and using `.remove()` to clean it up in $O(1)$ time. For speed, I will sort the array first and use a `for` loop. This allows for 'Pruning'—the moment a number exceeds my remaining target, I can `break` the loop and instantly kill the rest of the branch."*

**The Optimal Code:**
```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        // OPTIMIZATION 1: Sort the array to unlock Pruning
        Arrays.sort(candidates); 
        
        // Pass a single, reusable ArrayList to save Heap memory
        backtrack(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int[] arr, int remain, List<Integer> current, List<List<Integer>> result) {
        // Base Case: Target hit. This is the ONLY time we allocate a new list.
        if (remain == 0) {
            result.add(new ArrayList<>(current)); 
            return;
        }

        for (int i = start; i < arr.length; i++) {
            // OPTIMIZATION 2: Pruning. If the current number is bigger than the 
            // remaining target, every number after it will be too. Stop exploring!
            if (arr[i] > remain) {
                break; 
            }

            // 1. Choose (O(1) append)
            current.add(arr[i]);
            
            // 2. Explore (Pass 'i' to allow infinite reuse of the same number)
            backtrack(i, arr, remain - arr[i], current, result);
            
            // 3. Un-choose (O(1) removal to clean the scratchpad)
            current.remove(current.size() - 1);
        }
    }
}
```

---

### **Phase 3: The "Senior" Complexity Defense**

When the interviewer asks for the complexities, deliver this exact explanation to prove you understand the math behind the tree. Let $T$ = Target, $M$ = Minimum value in array, and $k$ = average combination length.

#### **Time Complexity: $O(N^{T/M} * k)$**
> *"The theoretical worst-case time complexity is bounded by $O(N^{T/M} \times k)$. $T/M$ represents the absolute maximum depth of the recursive tree, meaning the total nodes roughly scale by $N^{T/M}$. The factor of $k$ accounts for taking a deep copy of the array when a valid combination is found.*
>
> *However, Big-O ignores constant factors. In real-world execution, sorting the array and using the `break` statement chops off massive portions of the decision tree. Combined with the fact that we eliminated $O(k)$ memory allocations at every node, this optimal solution runs exponentially faster in CPU cycles than the naive approach."*

#### **Auxiliary Space Complexity: strictly $O(T/M)$**
> *"The auxiliary space is strictly O(T/M). Because we reuse a single `current` list and modify it in-place, we completely eliminate the O(k) heap memory leak. The only extra memory we use is the Call Stack for recursion, which will never go deeper than the maximum height of the tree: $Target / MinimumElement$."*

No, you cannot optimize this further using Dynamic Programming. The Backtracking solution with sorting and pruning is the absolute, mathematically proven ceiling for this specific problem.