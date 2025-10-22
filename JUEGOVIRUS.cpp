#include <iostream>
#include <vector>
#include <fstream>
#include <string>

using namespace std;

// Estructura para almacenar los datos del paciente
struct Paciente {
    string nombre;
    int edad;
    string genero;
    string motivoConsulta;
};

// Función para ingresar datos de un paciente
Paciente ingresarPaciente() {
    Paciente p;
    cout << "\n--- Ingreso de Datos del Paciente ---\n";
    cout << "Nombre: ";
    getline(cin, p.nombre);
    cout << "Edad: ";
    cin >> p.edad;
    cin.ignore(); // Limpiar el buffer
    cout << "Género: ";
    getline(cin, p.genero);
    cout << "Motivo de consulta: ";
    getline(cin, p.motivoConsulta);
    return p;
}

// Función para guardar pacientes en archivo
void guardarEnArchivo(const vector<Paciente>& pacientes) {
    ofstream archivo(C:\Users\cesaa\OneDrive\Desktop\Pacientes.txt, ios::app); // Modo append
    if (!archivo) {
        cerr << "Error al abrir el archivo.\n";
        return;
    }

    for (const auto& p : pacientes) {
        archivo << "Nombre: " << p.nombre << "\n";
        archivo << "Edad: " << p.edad << "\n";
        archivo << "Género: " << p.genero << "\n";
        archivo << "Motivo de consulta: " << p.motivoConsulta << "\n";
        archivo << "-----------------------------\n";
    }

    archivo.close();
    cout << "\nDatos guardados exitosamente en 'pacientes.txt'.\n";
}

int main() {
    vector<Paciente> pacientes;
    int opcion;

    do {
        cout << "\n=== Menú de Recepción Hospitalaria ===\n";
        cout << "1. Ingresar nuevo paciente\n";
        cout << "2. Guardar pacientes en archivo\n";
        cout << "3. Salir\n";
        cout << "Seleccione una opción: ";
        cin >> opcion;
        cin.ignore(); // Limpiar el buffer

        switch (opcion) {
            case 1: {
                Paciente nuevo = ingresarPaciente();
                pacientes.push_back(nuevo);
                break;
            }
            case 2:
                guardarEnArchivo(pacientes);
                pacientes.clear(); // Limpiar el vector después de guardar
                break;
            case 3:
                cout << "Saliendo del sistema...\n";
                break;
            default:
                cout << "Opción inválida. Intente de nuevo.\n";
        }
    } while (opcion != 3);

    return 0;
}
