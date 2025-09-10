import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(10);
        arr.add(20);
        arr.add(30);

        // Insertar elemento
        arr.add(40);

        // Recorrer e imprimir
        System.out.println("Elementos del arreglo:");
        for (int x : arr) {
            System.out.println(x);
        }
    }
}
