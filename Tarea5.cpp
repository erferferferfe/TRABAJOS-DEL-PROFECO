// ORDENAMIENTO POR SELECCIÓN (C++)
#include <iostream>
using namespace std;

int main() {
    int numeros[] = {64, 34, 25, 12, 22, 11, 90};
    int n = sizeof(numeros) / sizeof(numeros[0]);

    cout << "Lista original: ";
    for (int i = 0; i < n; i++) cout << numeros[i] << " ";
    cout << endl;

    for (int i = 0; i < n - 1; i++) {
        int min_idx = i;
        for (int j = i + 1; j < n; j++) {
            if (numeros[j] < numeros[min_idx]) {
                min_idx = j;
            }
        }
        int temp = numeros[min_idx];
        numeros[min_idx] = numeros[i];
        numeros[i] = temp;
    }

    cout << "Lista ordenada: ";
    for (int i = 0; i < n; i++) cout << numeros[i] << " ";
    cout << endl;

    return 0;
}