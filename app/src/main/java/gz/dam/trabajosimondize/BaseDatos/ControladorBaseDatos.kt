package gz.dam.trabajosimondize.BaseDatos

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import gz.dam.trabajosimondize.data.model.Record
import gz.dam.trabajosimondize.data.repository.InterfazRecord
import java.util.Date

/**
 * Esta clase actúa como un repositorio que gestiona los datos de puntuaciones
 * utilizando la base de datos SQLite a través de [DbHelper].
 *
 * Implementa la interfaz [InterfazRecord], por lo que cumple el mismo "contrato"
 * que el antiguo `ControladorPreference`, haciendo que el cambio sea transparente
 * para el ViewModel.
 */
class ControladorBaseDatos(context: Context) : InterfazRecord {

    private val dbHelper = DbHelper(context)

    /**
     * Inserta una nueva puntuación en la base de datos.
     *
     * A diferencia del método con SharedPreferences, este método no actualiza, sino que
     * añade una nueva fila por cada partida terminada, permitiendo un historial.
     */
    override fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int {
        val db = dbHelper.writableDatabase

        // Crea un nuevo mapa de valores, donde los nombres de las columnas son las claves.
        val values = ContentValues().apply {
            put(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION, nuevoRecord)
            put(Contract.PuntuacionEntry.COLUMN_NAME_FECHA, dataActual.time) // Guardamos la fecha como un Long
        }

        // Inserta la nueva fila, devolviendo el ID de la nueva fila.
        val newRowId = db.insert(Contract.PuntuacionEntry.TABLE_NAME, null, values)

        db.close()

        return if (newRowId != -1L) 1 else 0 // Devuelve 1 si la inserción fue exitosa
    }

    /**
     * Obtiene la puntuación más alta (el récord) de la base de datos.
     */
    override fun obtenerRecord(context: Context): Record {
        val db = dbHelper.readableDatabase

        // Define la proyección (qué columnas quieres obtener).
        val projection = arrayOf(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION, Contract.PuntuacionEntry.COLUMN_NAME_FECHA)

        // Ordena los resultados por puntuación en orden descendente para que el récord sea el primero.
        val sortOrder = "${Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION} DESC"

        val cursor = db.query(
            Contract.PuntuacionEntry.TABLE_NAME, // La tabla a consultar
            projection,                       // Las columnas a devolver
            null,                          // Cláusula WHERE (null para todas las filas)
            null,                       // Valores para el WHERE
            null,                          // No agrupar las filas
            null,                           // No filtrar por grupos de filas
            sortOrder,                        // El orden de clasificación
            "1"                               // Limita el resultado a 1 fila (el récord)
        )

        var recordPuntuacion = 0
        var recordFecha = Date() // Fecha por defecto si no hay récords

        // Itera sobre el cursor (aunque solo debería haber una fila).
        with(cursor) {
            if (moveToNext()) {
                recordPuntuacion = getInt(getColumnIndexOrThrow(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION))
                val fechaLong = getLong(getColumnIndexOrThrow(Contract.PuntuacionEntry.COLUMN_NAME_FECHA))
                recordFecha = Date(fechaLong)
            }
        }
        cursor.close()
        db.close()

        // Actualiza y devuelve el objeto Record
        Record.valorRecord = recordPuntuacion
        Record.fechaSuperacion = recordFecha
        return Record
    }
}
