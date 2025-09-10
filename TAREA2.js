class Alumno {
  constructor(nombre, edad) {
    this.nombre = nombre;
    this.edad = edad;
  }
}

const alumnos = [
  new Alumno("Ana", 20),
  new Alumno("Luis", 22),
  new Alumno("María", 19)
];

console.log("Lista de alumnos:");
for (const a of alumnos) {
  console.log(`${a.nombre} - ${a.edad} años`);
}
