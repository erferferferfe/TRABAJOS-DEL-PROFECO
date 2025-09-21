import java.util.*;
// CESAR ALEJANDRO LOPEZ PEREZ 203
public class Conecta4 {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final char VACIO = '.';
    public static final char FICHA1 = 'X';
    public static final char FICHA2 = 'O';

    private char[][] tablero;
    private Scanner entrada;
    private String jugador1;
    private String jugador2;

    public Conecta4() {
        tablero = new char[FILAS][COLUMNAS];
        entrada = new Scanner(System.in);
        limpiarTablero();
    }

    private void limpiarTablero() {
        for (int f = 0; f < FILAS; f++)
            Arrays.fill(tablero[f], VACIO);
    }

    private void mostrarTablero() {
        System.out.println();
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                System.out.print(tablero[f][c] + " ");
            }
            System.out.println();
        }
        for (int c = 0; c < COLUMNAS; c++) {
            System.out.print((c + 1) + " ");
        }
        System.out.println("\n");
    }

    private boolean columnaValida(int col) {
        return col >= 0 && col < COLUMNAS && tablero[0][col] == VACIO;
    }

    private int soltarFicha(int col, char ficha) {
        if (!columnaValida(col)) return -1;
        for (int f = FILAS - 1; f >= 0; f--) {
            if (tablero[f][col] == VACIO) {
                tablero[f][col] = ficha;
                return f;
            }
        }
        return -1;
    }

    private boolean tableroLleno() {
        for (int c = 0; c < COLUMNAS; c++) {
            if (tablero[0][c] == VACIO) return false;
        }
        return true;
    }

    private boolean hayGanador(char ficha) {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                if (tablero[f][c] == ficha && tablero[f][c+1] == ficha &&
                    tablero[f][c+2] == ficha && tablero[f][c+3] == ficha)
                    return true;
            }
        }
        for (int c = 0; c < COLUMNAS; c++) {
            for (int f = 0; f <= FILAS - 4; f++) {
                if (tablero[f][c] == ficha && tablero[f+1][c] == ficha &&
                    tablero[f+2][c] == ficha && tablero[f+3][c] == ficha)
                    return true;
            }
        }
        for (int f = 0; f <= FILAS - 4; f++) {
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                if (tablero[f][c] == ficha && tablero[f+1][c+1] == ficha &&
                    tablero[f+2][c+2] == ficha && tablero[f+3][c+3] == ficha)
                    return true;
            }
        }
        for (int f = 3; f < FILAS; f++) {
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                if (tablero[f][c] == ficha && tablero[f-1][c+1] == ficha &&
                    tablero[f-2][c+2] == ficha && tablero[f-3][c+3] == ficha)
                    return true;
            }
        }
        return false;
    }

    private int turnoJugador(String nombre, char ficha) {
        while (true) {
            System.out.print(nombre + " (" + ficha + ") - Elige columna (1-" + COLUMNAS + "): ");
            String linea = entrada.nextLine().trim();
            try {
                int col = Integer.parseInt(linea) - 1;
                if (!columnaValida(col)) {
                    System.out.println("Columna inválida o llena. Intenta otra.");
                    continue;
                }
                int fila = soltarFicha(col, ficha);
                return fila != -1 ? col : -1;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número (1-" + COLUMNAS + ").");
            }
        }
    }

    private void jugar() {
        limpiarTablero();
        mostrarTablero();
        char turno = FICHA1;
        while (true) {
            if (turno == FICHA1) {
                turnoJugador(jugador1, FICHA1);
            } else {
                turnoJugador(jugador2, FICHA2);
            }
            mostrarTablero();
            if (hayGanador(turno)) {
                System.out.println("¡Gana " + (turno == FICHA1 ? jugador1 : jugador2) + "!");
                break;
            }
            if (tableroLleno()) {
                System.out.println("Empate. ¡Tablero lleno!");
                break;
            }
            turno = (turno == FICHA1) ? FICHA2 : FICHA1;
        }
    }

    private void menu() {
        System.out.println("=== CONECTA 4 ===");
        System.out.print("Nombre del Jugador 1 (X): ");
        jugador1 = entrada.nextLine().trim();
        System.out.print("Nombre del Jugador 2 (O): ");
        jugador2 = entrada.nextLine().trim();
        while (true) {
            jugar();
            System.out.println("\n¿Jugar otra partida? (s/n)");
            String otra = entrada.nextLine().trim().toLowerCase();
            if (!otra.equals("s") && !otra.equals("si")) {
                System.out.println("Saliendo del juego. ¡Hasta luego!");
                return;
            }
        }
    }

    public static void main(String[] args) {
        Conecta4 juego = new Conecta4();
        juego.menu();
    }
}