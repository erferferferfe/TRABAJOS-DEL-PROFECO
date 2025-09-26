print ("AREGLOS ACA TODOS CHIDOS")
arr = [int(input("Ingresa un numero: ")) for _ in range(10)]
print("Orden normal:", arr)
arr_inverso = []
for i in range(len(arr)-1, -1, -1):
    arr_inverso.append(arr[i])
print("Orden inverso:", arr_inverso)