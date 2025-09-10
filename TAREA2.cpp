#include <iostream>
#include <string>
using namespace std;

class Alumno {
public:
    string nombre;
    int edad;
    Alumno(string n, int e) : nombre(n), edad(e) {}
};

int main() {
    Alumno alumnos[] = {
        Alumno("Ana", 20),
        Alumno("Luis", 22),
        Alumno("María", 19)
    };

    cout << "Lista de alumnos:\n";
    for (auto &a : alumnos) {
        cout << a.nombre << " - " << a.edad << " años" << endl;
    }
    return 0;
}