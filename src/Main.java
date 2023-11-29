class Algo1 {
    private int[] arr; // Array to be sorted



    int kElement;

    public Algo1(int[] arr, int k) {
        this.arr = arr;
        sort(this.arr, 0, this.arr.length - 1);
        this.kElement = arr[k-1];
    }
    public int getkElement() {
        return kElement;
    }
    private void merge(int[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, middle + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;

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

        System.arraycopy(L, i, arr, k, n1 - i);
        System.arraycopy(R, j, arr, k, n2 - j);
    }

    private void sort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            sort(arr, left, middle);
            sort(arr, middle + 1, right);
            merge(arr, left, middle, right);
        }
    }

    public void printArray() {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}

class QuickSelect {
    public static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }

        int pivotIndex = partition(arr, low, high);
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, low, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, high, k);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

class QuickSelectMM{

}

public class Main {
    public static void main(String[] args) {
        int k = 3;
        int[] arr = {12, 11, 13, 5, 6, 7};
        Algo1 algo1 = new Algo1(arr, 3);
        algo1.printArray();
        System.out.println("The " + k + "th smallest element is: " + algo1.getkElement());


        int kthSmallest = QuickSelect.quickSelect(arr, 0, arr.length - 1, k - 1);
        System.out.println("The " + k + "th smallest element is: " + kthSmallest);
    }
}