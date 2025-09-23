// MÉTODO DE ORDENAMIENTO BURBUJA (Java)
public class Burbuja {
    public static void main(String[] args) {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        System.out.print("Lista original: ");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        System.out.println();

        int n = numeros.length;

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

        System.out.print("Lista ordenada: ");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
    }
}