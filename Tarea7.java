class QuickSortDemo {
    // Method to perform QuickSort on an array between indices 'start' and 'end'
    static void quickSort(int[] arr, int start, int end) {
        // Base case: if the segment has 1 or 0 elements, it's already sorted
        if (start >= end) return;

        // Choose the pivot as the middle element
        int pivot = arr[(start + end) / 2];

        // Initialize pointers for partitioning
        int i = start, j = end;

        // Partition the array: elements < pivot to the left, > pivot to the right
        while (i <= j) {
            // Move 'i' until finding an element >= pivot
            while (arr[i] < pivot) i++;
            // Move 'j' until finding an element <= pivot
            while (arr[j] > pivot) j--;
            // Swap elements if i hasn't crossed j
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        // Recursively sort the left and right partitions
        quickSort(arr, start, j);
        quickSort(arr, i, end);
    }

    // Main method: entry point of the program
    public static void main(String[] args) {
        // Example array to sort
        int[] data = {8, 3, 1, 7, 0, 10, 2};

        System.out.println("Original array:");
        System.out.println(java.util.Arrays.toString(data));

        // Call QuickSort to sort the entire array
        quickSort(data, 0, data.length - 1);

        System.out.println("\nSorted array:");
        System.out.println(java.util.Arrays.toString(data));
    }
}