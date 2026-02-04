package gz.dam.trabajosimondize.data.controller

import android.app.Application
import android.util.Log
import androidx.room.Room
import gz.dam.trabajosimondize.data.record.Record
import gz.dam.trabajosimondize.data.record.RecordRemote
import gz.dam.trabajosimondize.data.repository.RecordLocalRepository
import gz.dam.trabajosimondize.data.repository.RecordRemoteRepository
import gz.dam.trabajosimondize.data.repository.RecordSyncRepository
import gz.dam.trabajosimondize.data.room.AppDatabase
import gz.dam.trabajosimondize.data.sync.SyncManager
import gz.dam.trabajosimondize.util.NetworkUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Controlador que integra MongoDB con la base de datos local.
 * Proporciona operaciones CRUD que funcionan tanto en local como en remoto.
 *
 * @param applicationContext El contexto de la aplicación
 * @param mongoDbUri URI de conexión a MongoDB (opcional, puede estar en secrets.properties)
 */
class ControllerMongoSync(
    private val applicationContext: Application,
    private val mongoDbUri: String = ""
) {
    private val TAG_LOG = "ControllerMongoSync"
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    // Inicializar base de datos local
    private val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "RoomRecord"
    )
        .allowMainThreadQueries()
        .build()

    // Inicializar repositorios
    private val localRepository = RecordLocalRepository(db)
    private val remoteRepository = RecordRemoteRepository(mongoDbUri)
    val syncRepository = RecordSyncRepository(localRepository, remoteRepository)

    // Inicializar gerenciador de sincronización
    val syncManager = SyncManager(applicationContext, syncRepository)

    init {
        Log.d(TAG_LOG, "ControllerMongoSync inicializado")
    }

    /**
     * Obtiene el récord actual (máximo)
     * @return El objeto Record con los datos del récord máximo
     */
    fun getRecord(context: android.content.Context): Record {
        // Hacer la llamada de forma sincrónica para mantener compatibilidad
        var puntuacion = 0
        var fecha = LocalDateTime.parse("11/11/2011 11:11:11", formatter)

        try {
            // Intentar obtener del repositorio de sincronización
            val result = db.recordDao().getMaxRecord()
            if (result != null) {
                puntuacion = result.puntuacion ?: 0
                fecha = LocalDateTime.parse(result.fecha, formatter)
            }
        } catch (e: Exception) {
            Log.e(TAG_LOG, "Error al obtener récord: ${e.message}")
        }

        Record.recordPun = puntuacion
        Record.recordFeha = fecha
        return Record
    }

    /**
     * Guarda un nuevo récord tanto localmente como en MongoDB
     * @param valorRecord La puntuación del nuevo récord
     * @param fechaRecord La fecha del récord
     * @param context El contexto de la aplicación
     * @return 1 si fue exitoso, -1 si ocurrió un error
     */
    fun setRecord(
        valorRecord: Int,
        fechaRecord: LocalDateTime,
        context: android.content.Context
    ): Int {
        return try {
            // Crear el objeto RemoteRecord
            val recordRemote = RecordRemote(
                puntuacion = valorRecord,
                fecha = fechaRecord.format(formatter)
            )

            // Guardar en el repositorio de sincronización
            val result = db.recordDao().insertAll(
                gz.dam.trabajosimondize.data.record.RecordEntity(
                    id = null,
                    puntuacion = valorRecord,
                    fecha = fechaRecord.format(formatter)
                )
            )

            // Intentar sincronizar con MongoDB si hay conexión
            if (NetworkUtil.isNetworkAvailable(context)) {
                syncManager.syncNow()
            }

            Log.d(TAG_LOG, "Récord guardado exitosamente")
            1 // Éxito
        } catch (e: Exception) {
            Log.e(TAG_LOG, "Error al guardar récord: ${e.message}")
            -1 // Error
        }
    }

    /**
     * Obtiene todos los récords
     */
    suspend fun getAllRecords(): Result<List<RecordRemote>> {
        return syncRepository.readAllRecords()
    }

    /**
     * Elimina todos los récords
     */
    suspend fun deleteAllRecords(): Result<Unit> {
        return syncRepository.deleteAllRecords()
    }

    /**
     * Inicia la sincronización automática
     */
    fun startAutoSync() {
        syncManager.startPeriodicSync()
        Log.d(TAG_LOG, "Sincronización automática iniciada")
    }

    /**
     * Detiene la sincronización automática
     */
    fun stopAutoSync() {
        syncManager.stopPeriodicSync()
        Log.d(TAG_LOG, "Sincronización automática detenida")
    }

    /**
     * Realiza una sincronización manual inmediata
     */
    fun syncNow() {
        syncManager.syncNow()
        Log.d(TAG_LOG, "Sincronización manual iniciada")
    }

    /**
     * Verifica si hay conexión disponible
     */
    fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(applicationContext)
    }

    /**
     * Obtiene el tipo de conexión actual
     */
    fun getConnectionType(): String {
        return NetworkUtil.getConnectionType(applicationContext)
    }
}

