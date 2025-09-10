class Alumno:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

alumnos = [
    Alumno("Ana", 20),
    Alumno("Luis", 22),
    Alumno("María", 19)
]

print("Lista de alumnos:")
for a in alumnos:
    print(f"{a.nombre} - {a.edad} años")