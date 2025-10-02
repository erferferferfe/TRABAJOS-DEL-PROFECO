using System;   // Nos permite usar la clase Console para escribir y leer en pantalla

class QuickSortDemo
{
    // ----------------------------
    // Método QuickSort:
    // Ordena un arreglo de enteros entre los índices 'inicio' y 'fin' (inclusive).
    // ----------------------------
    static void QuickSort(int[] arreglo, int inicio, int fin)
    {
        // Caso base: si el segmento a ordenar tiene 1 o 0 elementos, ya está ordenado
        if (inicio >= fin) return;

        // 'pivot' es el valor con el que vamos a comparar para dividir el arreglo en dos partes
        int pivot = arreglo[(inicio + fin) / 2];

        // 'i' avanza desde la izquierda y 'j' desde la derecha
        int i = inicio, j = fin;

        // Bucle para colocar los elementos menores al pivot a la izquierda,
        // y los mayores a la derecha
        while (i <= j)
        {
            // Avanzar 'i' hasta encontrar un elemento >= pivot
            while (arreglo[i] < pivot) i++;

            // Retroceder 'j' hasta encontrar un elemento <= pivot
            while (arreglo[j] > pivot) j--;

            // Si 'i' aún no pasó a 'j', intercambiamos esos elementos
            if (i <= j)
            {
                int temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;
                i++;
                j--;
            }
        }

        // Ahora el arreglo está dividido en dos mitades:
        // [inicio..j] con valores <= pivot
        // [i..fin]    con valores >= pivot

        // Llamada recursiva para ordenar la mitad izquierda
        QuickSort(arreglo, inicio, j);

        // Llamada recursiva para ordenar la mitad derecha
        QuickSort(arreglo, i, fin);
    }

    // ----------------------------
    // Función Main: punto de entrada del programa
    // ----------------------------
    static void Main()
    {
        // Arreglo de ejemplo a ordenar
        int[] datos = { 8, 3, 1, 7, 0, 10, 2 };

        Console.WriteLine("Arreglo original:");
        Console.WriteLine(string.Join(", ", datos));

        // Llamamos a QuickSort para ordenar todo el arreglo (índices de 0 a Length-1)
        QuickSort(datos, 0, datos.Length - 1);

        Console.WriteLine("\nArreglo ordenado:");
        Console.WriteLine(string.Join(", ", datos));

        // Esperar a que el usuario presione una tecla antes de cerrar (opcional)
        Console.WriteLine("\nPresiona una tecla para salir...");
        Console.ReadKey();
    }
}