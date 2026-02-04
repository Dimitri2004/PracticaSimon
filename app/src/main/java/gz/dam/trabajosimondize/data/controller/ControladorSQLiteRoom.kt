package gz.dam.trabajosimondize.data.controller

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import gz.dam.trabajosimondize.data.Utility.Record
import gz.dam.trabajosimondize.data.Utility.RecordEntity
import gz.dam.trabajosimondize.data.model.HandlerRecord
import gz.dam.trabajosimondize.data.room.AppDatabase
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase controladora que implementa la lógica para guardar y recuperar récords
 * utilizando la base de datos Room.
 *
 * @param applicationContext El contexto de la aplicación, necesario para inicializar la base de datos.
 */
class ControllerRoomSQLite(applicationContext: Application) : HandlerRecord {
    private val TAG_LOG: String = "RommSQLITE"
    // Define un formato estándar para convertir las fechas a texto y viceversa.
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    // Inicializa la base de datos Room.
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "RoomRecord"
    )
        // IMPORTANTE: allowMainThreadQueries() permite ejecutar consultas en el hilo principal.
        // Esto se debe evitar en una aplicación real porque puede bloquear la interfaz de usuario.
        // Lo correcto sería usar corrutinas (suspend fun) para las operaciones de la base de datos.
        .allowMainThreadQueries()
        .build()
    
    // Obtiene una instancia del DAO (Data Access Object) para interactuar con la tabla de récords.
    val recordDao = db.recordDao()


    /**
     * Guarda un nuevo récord en la base de datos.
     *
     * @param valorRecord La puntuación del nuevo récord.
     * @param fechaRecord La fecha en la que se consiguió el récord.
     * @param context El contexto de la aplicación (no se usa en esta implementación).
     * @return 1 si la operación fue exitosa, -1 si hubo un error.
     */
    override fun setRecord(
        valorRecord: Int,
        fechaRecord: LocalDateTime,
        context: Context
    ): Int {
        try {
            // Crea una entidad `RecordEntity` con los datos de la partida.
            val record = RecordEntity(
                id = null, // El ID se genera automáticamente al ser autoincremental.
                puntuacion = valorRecord,
                fecha = fechaRecord.format(formatter), // Convierte la fecha a String para guardarla.
            )
            // Llama al método del DAO para insertar el nuevo récord en la base de datos.
            recordDao.insertAll(record)
            return 1 // Devuelve 1 para indicar que la inserción fue exitosa.
        }catch (e: Exception){
            Log.d(TAG_LOG,"Error al insertar $e")
            return -1 // Devuelve -1 si ocurre una excepción.
        }
    }
    /**
     * Obtiene el récord más alto guardado en la base de datos.
     *
     * @param context El contexto de la aplicación (no se usa en esta implementación).
     * @return El objeto Singleton [Record] actualizado con los datos del récord máximo.
     */
    override fun getRecord(context: Context): Record {
        // Llama al método del DAO para obtener el récord con la puntuación más alta.
        val record = recordDao.getMaxRecord()


        val fecha: LocalDateTime
        val puntuacion: Int
        // Comprueba si la base de datos devolvió un récord o si estaba vacía.
        if (record != null){
            // Si hay un récord, convierte la fecha (que está como String) de vuelta a un objeto LocalDateTime.
            fecha = LocalDateTime.parse(record.fecha, formatter)

            Log.d(TAG_LOG,"Error al insertar $fecha") // Este log parece tener un texto de error incorrecto.
            // Asigna la puntuación, o 0 si por alguna razón fuera nula.
            puntuacion = record.puntuacion ?: 0

        }
        else{
            // Si no hay récords en la base de datos, establece valores por defecto.
            fecha = LocalDateTime.parse("11/11/2011 11:11:11",formatter)
            Log.d(TAG_LOG,"Error al insertar $fecha") // Este log parece tener un texto de error incorrecto.
            puntuacion = 0
        }
        // Actualiza el objeto Singleton 'Record' con los datos obtenidos.
        // Este objeto es accesible desde otras partes de la app para mostrar el récord.
        Record.recordPun = puntuacion
        Record.recordFeha = fecha
        
        Log.d(TAG_LOG,"Record: $record")
        Log.d(TAG_LOG,"Record: $fecha")

        return Record
    }

}
