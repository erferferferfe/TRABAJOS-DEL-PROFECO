class Alumno {
    String nombre;
    int edad;

    Alumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}

public class Main {
    public static void main(String[] args) {
        Alumno[] alumnos = {
            new Alumno("Ana", 20),
            new Alumno("Luis", 22),
            new Alumno("María", 19)
        };

        System.out.println("Lista de alumnos:");
        for (Alumno a : alumnos) {
            System.out.println(a.nombre + " - " + a.edad + " años");
        }
    }
}
