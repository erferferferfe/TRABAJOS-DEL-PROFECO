// ORDENAMIENTO POR INSERCIÓN (C#)
using System;

class Insercion {
    static void Main() {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        Console.Write("Lista original: ");
        foreach (int n in numeros) Console.Write(n + " ");
        Console.WriteLine();

        for (int i = 1; i < numeros.Length; i++) {
            int clave = numeros[i];
            int j = i - 1;
            while (j >= 0 && numeros[j] > clave) {
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = clave;
        }

        Console.Write("Lista ordenada: ");
        foreach (int n in numeros) Console.Write(n + " ");
    }
}