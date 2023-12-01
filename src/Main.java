import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

class Algo1 {
    private int[] arr; // Array to be sorted
    int kElement; // To store the kth smallest element

    // Constructor to initialize the array and find the kth element
    public Algo1(int[] arr, int k) {
        this.arr = arr;
        sort(this.arr, 0, this.arr.length - 1); // Sort the array
        this.kElement = arr[k-1]; // Assign the kth smallest element after sorting
    }

    // Getter method for kth element
    public int getkElement() {
        return kElement;
    }

    // Merge function used in Merge Sort algorithm
    private void merge(int[] arr, int left, int middle, int right) {
        // Calculate the sizes of two subarrays to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Create temporary arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, middle + 1, R, 0, n2);

        // Merge the temporary arrays back into the original array
        int i = 0, j = 0; // Initial indexes of first and second subarrays
        int k = left; // Initial index of merged subarray

        // Merge process
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] and R[] if any
        System.arraycopy(L, i, arr, k, n1 - i);
        System.arraycopy(R, j, arr, k, n2 - j);
    }

    // Main function that sorts arr[l..r] using merge()
    private void sort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point to divide the array into two halves
            int middle = left + (right - left) / 2;

            // Sort first and second halves
            sort(arr, left, middle);
            sort(arr, middle + 1, right);

            // Merge the sorted halves
            merge(arr, left, middle, right);
        }
    }
}

class QuickSelect {
    // Main function of QuickSelect algorithm to find the kth smallest element.
    // It works similar to QuickSort but only processes one side of the pivot each time.
    public static int quickSelect(int[] arr, int low, int high, int k) {
        // If the subarray has only one element, return that element.
        if (low == high) {
            return arr[low];
        }

        // Partition the array and get the index of the pivot element.
        int pivotIndex = partition(arr, low, high);

        // If the pivot position is the same as k, we have found the kth smallest element.
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            // If k is less than the pivot index, then the kth smallest element is in the left subarray.
            return quickSelect(arr, low, pivotIndex - 1, k);
        } else {
            // If k is more than the pivot index, then the kth smallest element is in the right subarray.
            return quickSelect(arr, pivotIndex + 1, high, k);
        }
    }

    // Partition function used in QuickSort and QuickSelect algorithms.
    // It arranges elements around a pivot such that elements smaller than pivot
    // are on the left and larger ones are on the right.
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Choosing the last element as the pivot
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                i++; // Increment the index of smaller element
                swap(arr, i, j); // Swap the elements
            }
        }
        swap(arr, i + 1, high); // Swap the pivot element with the element at i + 1
        return i + 1; // Return the partitioning index
    }

    // Utility function to swap two elements in the array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


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
                median[i] = getMedian(arr, 5 * i + low, Math.min(5 * i + low + 4, high));
            }

            // Handling the last group which may have less than 5 elements
            if (n % 5 == 0) {
                median[i] = getMedian(arr, 5 * i + low, 5 * i + low + 4);
            } else {
                median[i] = getMedian(arr, 5 * i + low, low + n - 1);
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
    private static int getMedian(int[] arr, int low, int high) {
        // For small arrays, insertion sort is more efficient than full sorting
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements of arr[low..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr[low + (high - low) / 2]; // Return the middle element
    }

    // Swap function to swap two elements in an array
    // Swap function to swap two elements in an array
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Partition function used in the QuickSelect algorithm
    // It arranges elements around a pivot such that elements smaller than pivot
    // are on the left and larger ones are on the right
    private static int partitionPractise(int[] arr, int low, int high, int pivot) {
        int pivotIndex = -1;
        for (int i = low; i <= high; i++) {
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        swap(arr, pivotIndex, high); // Move pivot to end

        int storeIndex = low;
        for (int i = low; i < high; i++) {
            if (arr[i] < pivot) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, high); // Move pivot to its final place
        return storeIndex;
    }

}

public class Main {
    // Generates an array of random integers of a given size
    static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(6000000); // Assuming the range of numbers is 0-99
        }
        return arr;
    }
    // Prints the array
    static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        int maxArraySize = 6000000;
        int numberOfRuns = 10;

        // Arrays to store average runtimes
        double[] avgTimeMedianOfMedians = new double[maxArraySize];
        double[] avgTimeAlgo1 = new double[maxArraySize];
        double[] avgTimeQuickSelect = new double[maxArraySize];

        try (FileWriter writer = new FileWriter("algorithm_runtimes.csv")) {
            writer.write("Array Size,MedianOfMedians,Algo1,QuickSelect\n");

            for (int n = 0, size = 1; size <= maxArraySize; n++, size = (int) Math.pow(2, n)) {
                long totalTimeMedianOfMedians = 0;
                long totalTimeAlgo1 = 0;
                long totalTimeQuickSelect = 0;
                int[] arr = generateRandomArray(size);
                int k = new Random().nextInt(size) + 1;

                for (int run = 0; run < numberOfRuns; run++) {
                    // Algo1
                    long startTime = System.nanoTime();
                    new Algo1(arr.clone(), k).getkElement();
                    totalTimeAlgo1 += System.nanoTime() - startTime;
                    // MedianOfMedians
                    long startTime2 = System.nanoTime();
                    MedianOfMedians.getKth(arr.clone(), 0, arr.length - 1, k);
                    totalTimeMedianOfMedians += System.nanoTime() - startTime2;
                    // QuickSelect
                    long startTime3 = System.nanoTime();
                    QuickSelect.quickSelect(arr.clone(), 0, arr.length - 1, k - 1);
                    totalTimeQuickSelect += System.nanoTime() - startTime3;
                }

                // Calculate averages
                avgTimeMedianOfMedians[size - 1] = (double) totalTimeMedianOfMedians / numberOfRuns;
                avgTimeAlgo1[size - 1] = (double) totalTimeAlgo1 / numberOfRuns;
                avgTimeQuickSelect[size - 1] = (double) totalTimeQuickSelect / numberOfRuns;

                // Print and write results for each size
                System.out.println("Array Size: " + size + " 2^" + n);
                System.out.println("MedianOfMedians Average Runtime: " + avgTimeMedianOfMedians[size - 1]);
                System.out.println("Algo1 Average Runtime: " + avgTimeAlgo1[size - 1]);
                System.out.println("QuickSelect Average Runtime: " + avgTimeQuickSelect[size - 1]);
                System.out.println("---------------------------------");

                writer.write(size + "," +
                        avgTimeMedianOfMedians[size - 1] + "," +
                        avgTimeAlgo1[size - 1] + "," +
                        avgTimeQuickSelect[size - 1] + "\n");
            }
        }
    }

}