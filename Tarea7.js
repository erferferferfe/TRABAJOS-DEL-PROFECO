function quickSort(arr, start, end) {
    // Base case: if the segment has 1 or 0 elements, it's already sorted
    if (start >= end) return;

    // Choose the pivot as the middle element
    let pivot = arr[Math.floor((start + end) / 2)];

    // Initialize pointers for partitioning
    let i = start, j = end;

    // Partition the array: elements < pivot to the left, > pivot to the right
    while (i <= j) {
        // Move 'i' until finding an element >= pivot
        while (arr[i] < pivot) i++;
        // Move 'j' until finding an element <= pivot
        while (arr[j] > pivot) j--;
        // Swap elements if i hasn't crossed j
        if (i <= j) {
            [arr[i], arr[j]] = [arr[j], arr[i]]; // ES6 destructuring for swap
            i++;
            j--;
        }
    }

    // Recursively sort the left and right partitions
    quickSort(arr, start, j);
    quickSort(arr, i, end);
}

// Main demonstration
let data = [8, 3, 1, 7, 0, 10, 2];
console.log("Original array:");
console.log(data.join(", "));

// Call QuickSort to sort the entire array
quickSort(data, 0, data.length - 1);

console.log("\nSorted array:");
console.log(data.join(", "));