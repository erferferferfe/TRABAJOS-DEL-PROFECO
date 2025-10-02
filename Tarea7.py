def quick_sort(arr, start, end):
    # Base case: if the segment has 1 or 0 elements, it's already sorted
    if start >= end:
        return

    # Choose the pivot as the middle element
    pivot = arr[(start + end) // 2]

    # Initialize pointers for partitioning
    i, j = start, end

    # Partition the array: elements < pivot to the left, > pivot to the right
    while i <= j:
        # Move 'i' until finding an element >= pivot
        while arr[i] < pivot:
            i += 1
        # Move 'j' until finding an element <= pivot
        while arr[j] > pivot:
            j -= 1
        # Swap elements if i hasn't crossed j
        if i <= j:
            arr[i], arr[j] = arr[j], arr[i]
            i += 1
            j -= 1

    # Recursively sort the left and right partitions
    quick_sort(arr, start, j)
    quick_sort(arr, i, end)

# Main demonstration
if __name__ == "__main__":
    # Example array to sort
    data = [8, 3, 1, 7, 0, 10, 2]

    print("Original array:")
    print(", ".join(map(str, data)))

    # Call QuickSort to sort the entire array
    quick_sort(data, 0, len(data) - 1)

    print("\nSorted array:")
    print(", ".join(map(str, data)))