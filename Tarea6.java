// ORDENAMIENTO POR INSERCIÓN (Java)
public class Insercion {
    public static void main(String[] args) {
        int[] numeros = {64, 34, 25, 12, 22, 11, 90};

        System.out.print("Lista original: ");
        for (int n : numeros) System.out.print(n + " ");
        System.out.println();

        for (int i = 1; i < numeros.length; i++) {
            int clave = numeros[i];
            int j = i - 1;
            while (j >= 0 && numeros[j] > clave) {
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = clave;
        }

        System.out.print("Lista ordenada: ");
        for (int n : numeros) System.out.print(n + " ");
    }
}