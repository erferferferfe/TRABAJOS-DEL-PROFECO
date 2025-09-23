// MÉTODO DE ORDENAMIENTO BURBUJA (JavaScript)
// ================================
let numeros = [64, 34, 25, 12, 22, 11, 90];

console.log("Lista original:", numeros);

let n = numeros.length;

// Algoritmo burbuja
for (let i = 0; i < n - 1; i++) {
    for (let j = 0; j < n - 1 - i; j++) {
        if (numeros[j] > numeros[j + 1]) {
            let temp = numeros[j];
            numeros[j] = numeros[j + 1];
            numeros[j + 1] = temp;
        }
    }
}

console.log("Lista ordenada:", numeros);