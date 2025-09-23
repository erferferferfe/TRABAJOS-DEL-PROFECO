// MÉTODO DE ORDENAMIENTO BURBUJA (C#)
using System;

class Burbuja {
    static void Main() {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        Console.Write("Lista original: ");
        foreach (int num in numeros) {
            Console.Write(num + " ");
        }
        Console.WriteLine();

        int n = numeros.Length;

        // Algoritmo burbuja
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (numeros[j] > numeros[j + 1]) {
                    int temp = numeros[j];
                    numeros[j] = numeros[j + 1];
                    numeros[j + 1] = temp;
                }
            }
        }

        Console.Write("Lista ordenada: ");
        foreach (int num in numeros) {
            Console.Write(num + " ");
        }
    }
}