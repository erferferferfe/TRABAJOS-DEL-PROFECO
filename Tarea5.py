# ORDENAMIENTO POR SELECCIÓN (Python)
numeros = [64, 34, 25, 12, 22, 11, 90]

print("Lista original:", numeros)

n = len(numeros)
for i in range(n - 1):
    min_idx = i
    for j in range(i + 1, n):
        if numeros[j] < numeros[min_idx]:
            min_idx = j
    numeros[i], numeros[min_idx] = numeros[min_idx], numeros[i]

print("Lista ordenada:", numeros)