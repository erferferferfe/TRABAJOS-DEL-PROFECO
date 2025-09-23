# ORDENAMIENTO POR INSERCIÓN (Python)
numeros = [64, 34, 25, 12, 22, 11, 90]

print("Lista original:", numeros)

for i in range(1, len(numeros)):
    clave = numeros[i]
    j = i - 1
    while j >= 0 and numeros[j] > clave:
        numeros[j + 1] = numeros[j]
        j -= 1
    numeros[j + 1] = clave

print("Lista ordenada:", numeros)