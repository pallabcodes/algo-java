There are two type of sliding windows: fixed and variable

[ NOTE ] The question will most likely ask to find `longest/shortest, minmum / maximum value from an array or string`

# Fixed (window size is given i.e. k)

1. Window is maintained and its size will always be "k" 
2. use two pointer (int i = 0; j = 0) and then "j" will lopping till array or string length

# Variable (window size is not given i.e. k)

1. since k is not given then simply consider all sub-arrays
2. arr = [1, 2, 3, 4, 5]
3. here, sub-arrays are [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 3, 4, 5]
4. use two pointer (int i = 0; j = 0) and then "j" will lopping till array or string length

```java
// general format of code

class SlidingWindow {
    private static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 3;
        // System.out.println(maxSum(arr, k));
    }
    
    private static void Example(int[] arr, int k) {
        int n = arr.length;
        // here taken two pointers e.g. i, j
        int i = 0, j = 0;
        
        while(j < n) {
            int wSize = j - i + 1;
            
            if(wSize < k) {
                j++;
            } else if (wSize == k) {
                // some calculations
                
                // slide the window by doing
                i++;
                j++;
            }
        }
    }
}





```