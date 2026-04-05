This is one of the most notorious "Hard" problems on LeetCode because the optimal solution requires a significant leap in logic. In an interview setting, the interviewer wants to see if you can break away from the instinct to merge the arrays and instead focus on what a median mathematically represents: a partition.

Here is your interview-ready breakdown for LeetCode 4: Median of Two Sorted Arrays.

---

### The Core Concept
The median of a dataset divides it into two equal halves. If we have two sorted arrays, `A` and `B`, we don't need to sort them together to find the median. We just need to find a way to cut `A` and cut `B` such that:
1. The total number of elements on the left side of both cuts equals the total number of elements on the right side.
2. Every element on the left side is less than or equal to every element on the right side.

---

### 1. The "Brute Force" Solution: Merge and Find
In a real interview, you should briefly explain this approach first to establish a baseline, even if you don't write the full code for it unless asked.

**The Strategy:** We allocate a new array of size `m + n`. Using two pointers, we iterate through both input arrays, comparing elements and placing the smaller one into our new array until it is fully merged and sorted. Finally, we return the middle element (or the average of the two middle elements if the total length is even).

#### Java Implementation
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];
        
        int i = 0, j = 0, k = 0;
        
        // Merge the two arrays
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        // Exhaust remaining elements
        while (i < m) merged[k++] = nums1[i++];
        while (j < n) merged[k++] = nums2[j++];
        
        // Find the median
        int totalLen = m + n;
        if (totalLen % 2 == 1) {
            return merged[totalLen / 2];
        } else {
            return ((double) merged[totalLen / 2 - 1] + merged[totalLen / 2]) / 2.0;
        }
    }
}
```

#### Rigorous Complexity Analysis
* **Time Complexity:** **O(m + n)**
  * **Why:** In the worst-case scenario, we must touch every single element in both `nums1` (length $m$) and `nums2` (length $n$) exactly once to build the merged array.
* **Space Complexity:** **O(m + n)**
  * **Why:** We are allocating an entirely new array `merged` that requires memory proportional to the combined sizes of the two input arrays.

---

### 2. The Optimal Solution: Binary Search on the Partition
The problem specifically demands an **O(log(m + n))** time complexity. Whenever you see a logarithmic time constraint on sorted arrays, your immediate instinct must be Binary Search. 

**The Strategy:** Instead of searching for the median value directly, we binary search for the correct **partition index** in the smaller array. 
If we cut the smaller array `nums1` at index `partitionX`, the cut in `nums2` at `partitionY` is mathematically forced because the left half must contain half of the total elements:
$$\text{partitionY} = \frac{m + n + 1}{2} - \text{partitionX}$$

We have found the perfect partition when the largest elements on the left side are smaller than or equal to the smallest elements on the right side:
`maxLeftX <= minRightY` AND `maxLeftY <= minRightX`

#### Java Implementation
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure we always binary search on the smaller array to minimize the search space
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int x = nums1.length;
        int y = nums2.length;
        int low = 0;
        int high = x;
        
        while (low <= high) {
            int partitionX = low + (high - low) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX;
            
            // Handle edge cases where the partition is at the extreme ends
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];
            
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];
            
            // Check if we have found the correct partition
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // If total length is even
                if ((x + y) % 2 == 0) {
                    return ((double) Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
                } 
                // If total length is odd
                else {
                    return (double) Math.max(maxLeftX, maxLeftY);
                }
            } 
            // We are too far right in nums1, move left
            else if (maxLeftX > minRightY) {
                high = partitionX - 1;
            } 
            // We are too far left in nums1, move right
            else {
                low = partitionX + 1;
            }
        }
        
        // If arrays are sorted, the loop will always return a valid median.
        // Reaching here means input arrays were not sorted properly.
        throw new IllegalArgumentException("Input arrays are not sorted.");
    }
}
```

#### Rigorous Complexity Analysis
* **Time Complexity:** **O(log(min(m, n)))**
  * **Why:** We force the algorithm to perform binary search exclusively on the smaller array. The size of the search space is $\min(m, n)$. A binary search halves the search space at each step, resulting in a logarithmic number of operations relative to the smaller array's length. This is strictly faster than the required $O(\log(m + n))$.
* **Space Complexity:** **O(1)**
  * **Why:** We only store a few integer variables (`low`, `high`, `partitionX`, `partitionY`, and the four edge variables) to track indices and values. No dynamic memory is allocated that scales with the size of the inputs.