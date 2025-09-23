// ORDENAMIENTO POR SELECCIÓN (C#)
using System;

class Seleccion {
    static void Main() {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        Console.Write("Lista original: ");
        foreach (int n in numeros) Console.Write(n + " ");
        Console.WriteLine();

        int nLen = numeros.Length;
        for (int i = 0; i < nLen - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < nLen; j++) {
                if (numeros[j] < numeros[min_idx]) {
                    min_idx = j;
                }
            }
            int temp = numeros[min_idx];
            numeros[min_idx] = numeros[i];
            numeros[i] = temp;
        }

        Console.Write("Lista ordenada: ");
        foreach (int n in numeros) Console.Write(n + " ");
    }
}