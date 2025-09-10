using System;

class Alumno {
    public string Nombre { get; set; }
    public int Edad { get; set; }

    public Alumno(string nombre, int edad) {
        Nombre = nombre;
        Edad = edad;
    }
}

class Program {
    static void Main() {
        Alumno[] alumnos = {
            new Alumno("Ana", 20),
            new Alumno("Luis", 22),
            new Alumno("María", 19)
        };

        Console.WriteLine("Lista de alumnos:");
        foreach (var a in alumnos) {
            Console.WriteLine($"{a.Nombre} - {a.Edad} años");
        }
    }
}