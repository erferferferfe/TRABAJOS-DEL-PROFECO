using System;
using System.Collections.Generic;

class Program {
    static void Main() {
        List<int> arr = new List<int> {10, 20, 30};

        // Insertar elemento
        arr.Add(40);

        // Recorrer e imprimir
        Console.WriteLine("Elementos del arreglo:");
        foreach (int x in arr) {
            Console.WriteLine(x);
        }
    }
}