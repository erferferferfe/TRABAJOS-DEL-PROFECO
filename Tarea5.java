// ORDENAMIENTO POR SELECCIÓN (Java)
public class Seleccion {
    public static void main(String[] args) {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        System.out.print("Lista original: ");
        for (int n : numeros) System.out.print(n + " ");
        System.out.println();

        int nLen = numeros.length;
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

        System.out.print("Lista ordenada: ");
        for (int n : numeros) System.out.print(n + " ");
    }
}