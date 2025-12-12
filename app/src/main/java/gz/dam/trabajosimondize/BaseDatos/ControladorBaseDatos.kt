package gz.dam.trabajosimondize.BaseDatos

import android.content.ContentValues
import android.content.Context
import gz.dam.trabajosimondize.data.model.Record
import gz.dam.trabajosimondize.data.repository.InterfazRecord
import java.util.Date

/**
 * Esta clase actúa como un repositorio que gestiona los datos de puntuaciones
 * utilizando la base de datos SQLite a través de [DbHelper].
 *
 * Implementa la interfaz [InterfazRecord], por lo que cumple el mismo "contrato"
 * que el antiguo `ControladorPreference`, haciendo que el cambio de sistema de guardado
 * (de SharedPreferences a SQLite) sea transparente para el ViewModel.
 */
class ControladorBaseDatos(context: Context) : InterfazRecord {

    // Mantenemos una única instancia del DbHelper, que es el que realmente crea y gestiona la base de datos.
    private val dbHelper = DbHelper(context)

    /**
     * Inserta la puntuación de una partida en la base de datos.
     * A diferencia del método con SharedPreferences, este método no "actualiza" el récord,
     * sino que AÑADE una nueva fila a la tabla por cada partida, guardando un historial.
     *
     * @param context El contexto de la aplicación (requerido por la interfaz).
     * @param nuevoRecord La puntuación de la partida que acaba de terminar.
     * @param dataActual La fecha en la que se terminó la partida.
     * @return 1 si la inserción fue exitosa, 0 si falló.
     */
    override fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int {
        // Obtenemos la base de datos en modo escritura.
        // SQLiteOpenHelper gestiona internamente si la conexión ya está abierta o si necesita crearla.
        val db = dbHelper.writableDatabase

        // ContentValues es como un mapa que asocia los nombres de las columnas con los valores que queremos insertar.
        val values = ContentValues().apply {
            put(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION, nuevoRecord)
            put(Contract.PuntuacionEntry.COLUMN_NAME_FECHA, dataActual.toString()) // Guardamos la fecha como un String
        }

        // Inserta la nueva fila en la tabla. El método devuelve el ID de la fila insertada, o -1 si hubo un error.
        val newRowId = db.insert(Contract.PuntuacionEntry.TABLE_NAME, null, values)

        // NO CERRAMOS LA BASE DE DATOS. Dejamos que el sistema la gestione para que esté disponible
        // para otras operaciones y para que el Database Inspector pueda verla.
        // db.close()

        return if (newRowId != -1L) 1 else 0 // Devolvemos 1 si la operación fue un éxito.
    }

    /**
     * Obtiene la puntuación más alta (el récord) de toda la base de datos.
     *
     * @param context El contexto de la aplicación (requerido por la interfaz).
     * @return Un objeto [Record] con la puntuación más alta y su fecha.
     */
    override fun obtenerRecord(context: Context): Record {
        val db = dbHelper.readableDatabase

        // Define qué columnas de la tabla nos interesan para esta consulta.
        val projection = arrayOf(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION, Contract.PuntuacionEntry.COLUMN_NAME_FECHA)
        
        // Define el orden de los resultados: de mayor puntuación a menor.
        val sortOrder = "${Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION} DESC"
        
        // Limita la consulta para que solo nos devuelva la primera fila, que será el récord.
        val limit = "1"

        // Ejecuta la consulta SQL a la base de datos.
        val cursor = db.query(
            Contract.PuntuacionEntry.TABLE_NAME, // La tabla a consultar
            projection,                      // Las columnas a devolver
            null,                         // Cláusula WHERE (null para todas las filas)
            null,                      // Valores para el WHERE
            null,                         // No agrupar las filas
            null,                          // No filtrar por grupos de filas
            sortOrder,                       // El orden de clasificación
            limit                            // Límite de resultados
        )

        var recordPuntuacion = 0
        var recordFecha = Date() // Valores por defecto si la tabla está vacía

        // El `with(cursor)` nos permite trabajar con el cursor de forma segura.
        // Un cursor es un puntero que se mueve sobre los resultados de la consulta.
        with(cursor) {
            // `moveToNext()` mueve el puntero a la primera fila de resultados. Si no hay resultados, devuelve false.
            if (moveToNext()) {
                // Obtenemos los valores de las columnas por su nombre.
                recordPuntuacion = getInt(getColumnIndexOrThrow(Contract.PuntuacionEntry.COLUMN_NAME_PUNTUACION))
                val fechaLong = getLong(getColumnIndexOrThrow(Contract.PuntuacionEntry.COLUMN_NAME_FECHA))
                recordFecha = Date(fechaLong) // Convertimos el número (Long) de vuelta a un objeto Date.
            }
        }
        // Es FUNDAMENTAL cerrar siempre el cursor cuando terminamos de usarlo para liberar recursos.
        cursor.close()
        
        // NO CERRAMOS LA BASE DE DATOS por la misma razón que en el método anterior.
        // db.close()

        // Actualizamos el objeto Singleton `Record` con los datos obtenidos y lo devolvemos.
        Record.valorRecord = recordPuntuacion
        Record.fechaSuperacion = recordFecha
        return Record
    }
}
