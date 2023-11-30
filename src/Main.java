import java.util.Arrays;

class MedianOfMedians {
    // Main function to find the kth smallest element in a given array.
    // It uses the Median of Medians algorithm for a worst-case linear time.
    public static int getKth(int[] arr, int low, int high, int k) {

        // Check if k is within the bounds of the array
        if (k > 0 && k <= high - low + 1) {
            // Calculate the number of elements in the current array segment
            int n = high - low + 1;

            // Create an array to store the medians of all the groups
            int i;
            int[] median = new int[(n + 4) / 5];

            // Divide the array into groups of 5 and find their medians
            for (i = 0; i < median.length - 1; i++) {
                median[i] = getMedian(Arrays.copyOfRange(arr, 5 * i + low, 5 * i + low + 4), 5);
            }

            // Handling the last group which may have less than 5 elements
            if (n % 5 == 0) {
                median[i] = getMedian(Arrays.copyOfRange(arr, 5 * i + low, 5 * i + low + 4), 5);
            } else {
                median[i] = getMedian(Arrays.copyOfRange(arr, 5 * i + low, 5 * i + low + (n % 5)), n % 5);
            }
            i++;

            // Find the median of the medians using recursive call
            int medOfMed = i == 1 ? median[i - 1]
                    : getKth(median, 0, i - 1, i / 2);

            // Partition the array around the median of medians
            int partition = partitionPractise(arr, low, high, medOfMed);

            // Check if we found the kth element
            if (partition - low == k - 1) {
                return arr[partition];
            }

            // Decide which part of the array to search next
            if (partition - low > k - 1) {
                return getKth(arr, low, partition - 1, k);
            }

            return getKth(arr, partition + 1, high, k - (partition + 1) + low);
        }

        // Return -1 if k is out of bounds
        return -1;
    }

    // Helper function to find the median of a small array (up to 5 elements)
    private static int getMedian(int[] arr, int n) {
        Arrays.sort(arr);
        return arr[n / 2];
    }

    // Swap function to swap two elements in an array
    private static void swap(int[] arr, int i, int index) {
        if (arr[i] == arr[index]) {
            return; // Skip swap if the elements are the same
        }
        int temp = arr[i];
        arr[i] = arr[index];
        arr[index] = temp;
    }

    // Partition function used in the QuickSelect algorithm
    // It arranges elements around a pivot such that elements smaller than pivot
    // are on the left and larger ones are on the right
    private static int partitionPractise(int[] arr, int low, int high, int pivot) {

        // Find the pivot element and move it to the end
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == pivot) {
                swap(arr, i, high);
                break;
            }
        }
        int index = low - 1;
        int i = low;

        // Rearrange elements around the pivot
        while (i < high) {
            if (arr[i] < pivot) {
                index++;
                swap(arr, i, index);
            }
            i++;
        }
        index++;
        swap(arr, index, high); // Place the pivot element in its correct position

        return index; // Return the index of the pivot
    }

}

public class Main {
    public static void main(String[] args) {

        // Example array
        int[] arr = {7, 10, 4, 3, 20, 15};

        // kth smallest element to find
        int k = 3; // For example, find the 3rd smallest element

        // Call the method and store the result
        int kthSmallest = MedianOfMedians.getKth(arr,0, arr.length - 1, k);

        // Output the result
        System.out.println("The " + k + "th smallest element is: " + kthSmallest);
    }
}