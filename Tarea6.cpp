// ORDENAMIENTO POR INSERCIÓN (C++)
#include <iostream>
using namespace std;

int main() {
    int numeros[] = {64, 34, 25, 12, 22, 11, 90};
    int n = sizeof(numeros) / sizeof(numeros[0]);

    cout << "Lista original: ";
    for (int i = 0; i < n; i++) cout << numeros[i] << " ";
    cout << endl;

    for (int i = 1; i < n; i++) {
        int clave = numeros[i];
        int j = i - 1;
        while (j >= 0 && numeros[j] > clave) {
            numeros[j + 1] = numeros[j];
            j--;
        }
        numeros[j + 1] = clave;
    }

    cout << "Lista ordenada: ";
    for (int i = 0; i < n; i++) cout << numeros[i] << " ";
    cout << endl;

    return 0;
}