package gz.dam.trabajosimondize.data.Utility

import java.time.LocalDateTime

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
    var recordPun: Int = 0
    var recordFeha: LocalDateTime = LocalDateTime.now()

}