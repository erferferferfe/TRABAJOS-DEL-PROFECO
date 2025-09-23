// ORDENAMIENTO POR SELECCIÓN (JavaScript)
let numeros = [64, 34, 25, 12, 22, 11, 90];

console.log("Lista original:", numeros);

let n = numeros.length;
for (let i = 0; i < n - 1; i++) {
    let min_idx = i;
    for (let j = i + 1; j < n; j++) {
        if (numeros[j] < numeros[min_idx]) {
            min_idx = j;
        }
    }
    let temp = numeros[min_idx];
    numeros[min_idx] = numeros[i];
    numeros[i] = temp;
}

console.log("Lista ordenada:", numeros);