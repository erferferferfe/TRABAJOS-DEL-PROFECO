# JUEGO DEL GATO
# ----------------------------
# Crear el tablero vacío
def crear_tablero():
    # Crea una lista de 3 filas y 3 columnas,
    # inicialmente todas vacías (con espacios " ").
    return [[" " for _ in range(3)] for _ in range(3)]
# Mostrar el tablero
def mostrar_tablero(tablero):
    # Imprime el tablero en pantalla para que los jugadores
    # vean en qué posiciones ya hay símbolos.
    print("\n")
    for fila in tablero:
        # Une los elementos de la fila con " | " para dar formato
        print(" | ".join(fila))
        print("-" * 5)  # Separador entre filas
    print("\n")
# Verificar si hay ganador
def hay_ganador(tablero, simbolo):
    # Revisa las 8 formas posibles de ganar:
    # 3 filas, 3 columnas y 2 diagonales.

    # Revisar filas
    for fila in tablero:
        if all(celda == simbolo for celda in fila):
            return True

    # Revisar columnas
    for col in range(3):
        if all(tablero[fila][col] == simbolo for fila in range(3)):
            return True

    # Revisar diagonal principal (de arriba izquierda a abajo derecha)
    if all(tablero[i][i] == simbolo for i in range(3)):
        return True

    # Revisar diagonal secundaria (de arriba derecha a abajo izquierda)
    if all(tablero[i][2 - i] == simbolo for i in range(3)):
        return True

    return False  # Si no cumple ninguna, no hay ganador

# Revisar si el tablero está lleno
def tablero_lleno(tablero):
    # Revisa si ya no queda ninguna casilla vacía (" ").
    return all(celda != " " for fila in tablero for celda in fila)
# Función principal del juego
def jugar():
    tablero = crear_tablero()        # Creamos el tablero vacío
    jugador = 1                      # Empieza el jugador 1
    simbolos = {1: "X", 2: "O"}      # Jugador 1 usa "X", jugador 2 usa "O"

    while True:  # Bucle principal: se repite hasta que alguien gane o empate
        mostrar_tablero(tablero)  # Mostramos el tablero
        print(f"Turno del jugador {jugador} ({simbolos[jugador]})")

        # ----------------------------
        # Pedir fila y columna al jugador
        # ----------------------------
        try:
            fila = int(input("Elige la fila (0, 1, 2): "))
            col = int(input("Elige la columna (0, 1, 2): "))
        except ValueError:
            # Si el jugador escribe algo que no es número
            print("Entrada inválida. Usa números.")
            continue

        # Validar que la posición esté dentro del rango 0-2
        if fila not in [0, 1, 2] or col not in [0, 1, 2]:
            print("Posición fuera de rango. Intenta de nuevo.")
            continue

        # Revisar si la casilla ya está ocupada
        if tablero[fila][col] != " ":
            print("Esa posición ya está ocupada. Intenta de nuevo.")
            continue

        # Si todo es válido, colocar el símbolo en el tablero
        tablero[fila][col] = simbolos[jugador]

        # Revisar si este jugador ganó
        if hay_ganador(tablero, simbolos[jugador]):
            mostrar_tablero(tablero)
            print(f"¡Jugador {jugador} ({simbolos[jugador]}) gana!")
            break  # Termina el juego

        # Revisar si hay empate
        if tablero_lleno(tablero):
            mostrar_tablero(tablero)
            print("¡Empate!")
            break  # Termina el juego

        # Cambiar de jugador
        jugador = 2 if jugador == 1 else 1


# Iniciar el juego
jugar()