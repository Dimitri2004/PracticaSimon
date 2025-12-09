package gz.dam.trabajosimondize.data.model


import java.util.Date

/**
 * Representa el modelo de datos del récord del juego.
 *
 * Al ser un `object` de Kotlin, se implementa como un Singleton, lo que garantiza
 * que solo existirá una única instancia de este objeto en toda la aplicación.
 *
 * Funciona como un contenedor en memoria para los datos del récord, que son
 * cargados y actualizados por un gestor de datos como [ControladorPreference].
 */
object Record {
    /**
     * Almacena la puntuación más alta conseguida.
     * Se inicializa en 0.
     */
    var valorRecord:Int = 0

    /**
     * Almacena la fecha y hora en la que se consiguió el récord.
     * Se inicializa con la fecha y hora actuales por defecto.
     */
    var fechaSuperacion: Date = Date()
}
