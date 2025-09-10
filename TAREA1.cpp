#include <iostream>
using namespace std;

int main() {
    int arr[] = {40, 50, 60, 70, 80, 90};
    cout << "Recorrido lineal (secuencial): ";
    cout << "\nLos elementos del array son: ";
    for (int x : arr) {
        cout << x << ' ';
    }
    cout << '\n';
    return 0;
}