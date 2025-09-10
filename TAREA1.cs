using System;

class Program {
    static void Main() {
        int[] arr = {40, 50, 60, 70, 80, 90};
        Console.Write("Recorrido lineal (secuencial): ");
        Console.Write("\nLos elementos del array son: ");
        foreach (var x in arr) {
            Console.Write(x + " ");
        }
        Console.WriteLine();
    }
}