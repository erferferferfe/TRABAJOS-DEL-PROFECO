// ORDENAMIENTO POR INSERCIÓN (JavaScript)
let numeros = [64, 34, 25, 12, 22, 11, 90];

console.log("Lista original:", numeros);

for (let i = 1; i < numeros.length; i++) {
    let clave = numeros[i];
    let j = i - 1;
    while (j >= 0 && numeros[j] > clave) {
        numeros[j + 1] = numeros[j];
        j--;
    }
    numeros[j + 1] = clave;
}

console.log("Lista ordenada:", numeros);