package gz.dam.trabajosimondize.BaseDatos

import android.provider.BaseColumns

/**
 * Define el "contrato" de la base de datos. Un contrato es un contenedor de constantes
 * que definen nombres de tablas, columnas, etc. Ayuda a evitar errores al escribir
 * los nombres de las columnas en diferentes partes del código.
 */
object Contract {

    // Constantes para la creación y versión de la base de datos.
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SimonDB.db"

    /**
     * Define la estructura de la tabla de puntuaciones.
     * Al implementar BaseColumns, heredamos la columna _ID, una práctica recomendada
     * que algunas partes de Android (como los CursorAdapters) esperan encontrar.
     */
    object PuntuacionEntry : BaseColumns {
        const val TABLE_NAME = "puntuaciones"
        const val COLUMN_NAME_PUNTUACION = "puntuacion"
        const val COLUMN_NAME_FECHA = "fecha"
    }

    /**
     * Sentencia SQL para crear la tabla de puntuaciones. Define tres columnas:
     * - _ID: Un entero autoincremental que actúa como clave primaria.
     * - puntuacion: Un entero para guardar la puntuación de la partida.
     * - fecha: Un entero (Long) para guardar la fecha en formato de milisegundos.
     */

    const val SQL_CREATE_ENTRIES = """
        CREATE TABLE ${PuntuacionEntry.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY,
            ${PuntuacionEntry.COLUMN_NAME_PUNTUACION} INTEGER,
            ${PuntuacionEntry.COLUMN_NAME_FECHA} INTEGER)"""

    /**
     * Sentencia SQL para eliminar la tabla. Es útil para resetear o actualizar
     * la estructura de la base de datos.
     */
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${PuntuacionEntry.TABLE_NAME}"
}
