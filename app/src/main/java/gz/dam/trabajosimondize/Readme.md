# Proyecto Simon Dice

Este proyecto es una aplicación Android que implementa el clásico juego "Simon Dice". Su objetivo principal no es solo ofrecer el juego, sino también servir como un caso de estudio para demostrar y comparar diferentes métodos de persistencia de datos en Android.

## Características Principales

-   **Juego Clásico de Simon:** Lógica completa para generar secuencias de colores, gestionar el turno del jugador y comprobar la respuesta.
-   **Sistema de Puntuación y Rondas:** La aplicación lleva la cuenta de la puntuación y la ronda actual.
-   **Gestión de Récord:** Se calcula y se guarda la puntuación más alta.
-   **Múltiples Mecanismos de Persistencia:** El récord se puede guardar usando tres tecnologías diferentes:
    1.  **SharedPreferences**
    2.  **SQLite** (con la clase `SQLiteOpenHelper`)
    3.  **Room** (la biblioteca de persistencia moderna recomendada por Google)

## Estructura del Proyecto

El código está organizado en varios paquetes clave que separan la interfaz, la lógica y el acceso a datos.

### Lógica Principal y UI

-   `MainActivity.kt`: Es el punto de entrada de la aplicación. Su única responsabilidad es crear el `ViewModel` y llamar a la interfaz principal.
-   `Interfaz.kt`: Contiene toda la interfaz de usuario del juego. Es responsable de dibujar los botones, mostrar la puntuación y reaccionar a los cambios de estado del `ViewModel`.
-   `ViewModel.kt`: Es el cerebro de la aplicación. Gestiona toda la lógica del juego (generar secuencias, comprobar la entrada del usuario, etc.) y el estado de la UI (`puntuacion`, `ronda`, `record`). Se comunica con los controladores de datos para guardar y cargar el récord.
-   `Datos.kt`: Fichero que contiene clases `enum` y `object` para definir constantes del juego, como los colores (`Colores`) y los diferentes estados del juego (`Estado`).

### Modelo de Datos

-   `data/model/Record.kt`: Es un objeto `object` (Singleton) que mantiene en memoria la puntuación y la fecha del récord actual. Sirve como un contenedor de datos accesible desde toda la aplicación.

### Controladores de Persistencia de Datos

Esta es la parte más importante del proyecto para entender las diferentes formas de guardar datos en Android. La interfaz `InterfazRecord.kt` define un contrato común que todos los controladores deben seguir.

1.  **`data/controller/ControladorPreference.kt`**:
    -   **Tecnología:** `SharedPreferences`.
    -   **Descripción:** Guarda el récord como un simple par clave-valor. Es ideal para guardar pequeñas cantidades de datos, como configuraciones o una única puntuación. Es la forma más sencilla de persistencia.
2.  **`data/controller/ControladorSQLite.kt`**:
    -   **Tecnología:** `SQLiteOpenHelper`.
    -   **Descripción:** Implementa el acceso a una base de datos SQLite de forma manual. Requiere escribir sentencias SQL para crear tablas (`ContratoSQLite.kt`) y para realizar las operaciones de inserción y consulta. Es el método tradicional y más verboso.
3.  **`data/controller/ControladorSQLiteRoom.kt`**:
    -   **Tecnología:** `Room`.
    -   **Descripción:** Utiliza la biblioteca Room, que es una capa de abstracción sobre SQLite. En lugar de escribir SQL a mano, se definen entidades (`RecordEntity.kt`), un DAO (`RecordDao.kt`) y una clase de base de datos (`AppDatabase.kt`). Room se encarga de generar el código SQL necesario, resultando en un código más seguro y fácil de mantener.

## Cómo Funciona

1.  La aplicación se inicia y `MainActivity` crea una instancia de `MyViewModel`.
2.  `MyViewModel` inicializa un controlador de datos (por ejemplo, `ControladorPreference`). Al iniciarse, pide al controlador el récord guardado para mostrarlo en pantalla.
3.  El usuario juega a través de `Interfaz.kt`, que notifica al `ViewModel` de cada botón pulsado.
4.  El `ViewModel` procesa la lógica del juego.
5.  Cuando una partida termina, el `ViewModel` llama al método `actualizarRecord()` del controlador, pasándole la nueva puntuación.
6.  El controlador (sea `SharedPreferences`, `SQLite` o `Room`) se encarga de guardar la nueva puntuación en el almacenamiento del dispositivo si esta supera el récord anterior.
