#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> arr = {10, 20, 30};

    // Insertar elemento
    arr.push_back(40);

    // Recorrer e imprimir
    cout << "Elementos del arreglo:\n";
    for (int x : arr) {
        cout << x << endl;
    }

    return 0;
}