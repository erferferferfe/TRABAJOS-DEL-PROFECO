// Autor: Cesar Alejandro Lopez Perez
#include <iostream>
#include <vector>
#include <cctype>
#include <limits>
using namespace std;

struct Barco {
    string nombre;
    int tam;
    int cantidad;
};

void mostrarTablero(const vector<vector<char>>& tablero) {
    cout << "   ";
    for (int c = 0; c < 10; c++)
        cout << c << " ";
    cout << endl;

    for (int f = 0; f < 10; f++) {
        cout << f << "  ";
        for (int c = 0; c < 10; c++) {
            cout << tablero[f][c] << " ";
        }
        cout << endl;
    }
}

bool colocarBarco(vector<vector<char>>& tablero, int fila, int col, char orientacion, int tam) {
    if (fila < 0 || fila >= 10 || col < 0 || col >= 10) return false;

    if (orientacion == 'H') {
        if (col + tam > 10) return false;
        for (int i = 0; i < tam; i++)
            if (tablero[fila][col + i] != '.') return false;
        for (int i = 0; i < tam; i++)
            tablero[fila][col + i] = '#';
    } else {
        if (fila + tam > 10) return false;
        for (int i = 0; i < tam; i++)
            if (tablero[fila + i][col] != '.') return false;
        for (int i = 0; i < tam; i++)
            tablero[fila + i][col] = '#';
    }
    return true;
}

// Función para contar los barcos restantes en un tablero
int barcosRestantes(const vector<vector<char>>& tablero) {
    int total = 0;
    for (int f = 0; f < 10; f++)
        for (int c = 0; c < 10; c++)
            if (tablero[f][c] == '#') total++;
    return total;
}

// Función de turno de disparo
void disparar(vector<vector<char>>& tableroOponente, vector<vector<char>>& tableroDisparos, int jugador) {
    int f, c;
    while (true) {
        cout << "\nJugador " << jugador << " dispara:\n";
        cout << "Fila (0-9): "; cin >> f;
        cout << "Columna (0-9): "; cin >> c;

        if (f < 0 || f > 9 || c < 0 || c > 9) {
            cout << "Coordenada invalida.\n";
            continue;
        }

        if (tableroDisparos[f][c] != '.') {
            cout << "Ya disparaste ahi!\n";
            continue;
        }

        if (tableroOponente[f][c] == '#') {
            cout << "Impacto!\n";
            tableroOponente[f][c] = 'X';
            tableroDisparos[f][c] = 'X';
        } else {
            cout << "Agua...\n";
            tableroDisparos[f][c] = 'O';
        }
        break;
    }
}

int main() {
    vector<vector<char>> tablero1(10, vector<char>(10, '.'));
    vector<vector<char>> tablero2(10, vector<char>(10, '.'));
    vector<vector<char>> disparos1(10, vector<char>(10, '.'));
    vector<vector<char>> disparos2(10, vector<char>(10, '.'));

    vector<Barco> flota = {
        {"Portaaviones", 5, 2},
        {"Acorazado",    4, 2},
        {"Crucero",      3, 2},
        {"Destructor",   2, 2},
        {"Submarino",    1, 2}
    };

    cout << "=== JUEGO BATALLA NAVAL ===\n";

    // COLOCAR BARCOS JUGADOR 1
    cout << "\n--- Jugador 1 coloca sus barcos ---\n";
    for (auto &b : flota) {
        for (int n = 1; n <= b.cantidad; n++) {
            bool colocado = false;
            while (!colocado) {
                cout << "\nColocando " << b.nombre << " #" << n << " (tam " << b.tam << ")\n";
                int f, c; char o;
                cout << "Fila: "; cin >> f;
                cout << "Columna: "; cin >> c;
                cout << "Orientacion (H/V): "; cin >> o; o = toupper(o);
                if (colocarBarco(tablero1, f, c, o, b.tam)) {
                    colocado = true;
                    mostrarTablero(tablero1);
                } else cout << "No se pudo colocar.\n";
            }
        }
    }

    // COLOCAR BARCOS JUGADOR 2
    cout << "\n--- Jugador 2 coloca sus barcos ---\n";
    for (auto &b : flota) {
        for (int n = 1; n <= b.cantidad; n++) {
            bool colocado = false;
            while (!colocado) {
                cout << "\nColocando " << b.nombre << " #" << n << " (tam " << b.tam << ")\n";
                int f, c; char o;
                cout << "Fila: "; cin >> f;
                cout << "Columna: "; cin >> c;
                cout << "Orientacion (H/V): "; cin >> o; o = toupper(o);
                if (colocarBarco(tablero2, f, c, o, b.tam)) {
                    colocado = true;
                    mostrarTablero(tablero2);
                } else cout << "No se pudo colocar.\n";
            }
        }
    }

    // BATALLA
    while (true) {
        mostrarTablero(disparos1);
        disparar(tablero2, disparos1, 1);
        if (barcosRestantes(tablero2) == 0) {
            cout << "\nJugador 1 GANO! Todos los barcos enemigos fueron hundidos.\n";
            break;
        }

        mostrarTablero(disparos2);
        disparar(tablero1, disparos2, 2);
        if (barcosRestantes(tablero1) == 0) {
            cout << "\nJugador 2 GANO! Todos los barcos enemigos fueron hundidos.\n";
            break;
        }
    }

    return 0;
}
