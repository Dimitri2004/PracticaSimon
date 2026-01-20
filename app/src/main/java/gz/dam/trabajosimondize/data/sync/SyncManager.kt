package gz.dam.trabajosimondize.data.sync

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import gz.dam.trabajosimondize.data.repository.RecordSyncRepository
import gz.dam.trabajosimondize.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Gestor de sincronización que coordina la sincronización de datos entre
 * la base de datos local y MongoDB.
 *
 * @param context El contexto de la aplicación
 * @param syncRepository El repositorio de sincronización
 */
class SyncManager(
    private val context: Context,
    private val syncRepository: RecordSyncRepository
) {
    private val TAG = "SyncManager"
    private val workManager = WorkManager.getInstance(context)
    private val scope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val SYNC_WORK_NAME = "record_sync_work"
        private const val SYNC_INTERVAL_MINUTES = 15L
    }

    /**
     * Inicia la sincronización automática periódica
     * Se ejecutará cada 15 minutos si es posible
     */
    fun startPeriodicSync() {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Log.w(TAG, "No hay conexión a internet, no se inicia sincronización periódica")
            return
        }

        Log.d(TAG, "Iniciando sincronización periódica")

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            SYNC_INTERVAL_MINUTES,
            TimeUnit.MINUTES
        )
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        Log.d(TAG, "Sincronización periódica programada cada $SYNC_INTERVAL_MINUTES minutos")
    }

    /**
     * Detiene la sincronización automática periódica
     */
    fun stopPeriodicSync() {
        Log.d(TAG, "Deteniendo sincronización periódica")
        workManager.cancelUniqueWork(SYNC_WORK_NAME)
    }

    /**
     * Ejecuta una sincronización manual inmediata
     */
    fun syncNow() {
        scope.launch {
            try {
                if (!NetworkUtil.isNetworkAvailable(context)) {
                    Log.w(TAG, "No hay conexión a internet para sincronización manual")
                    return@launch
                }

                Log.d(TAG, "Iniciando sincronización manual")
                val result = syncRepository.syncPendingRecords()

                result.onSuccess { count ->
                    Log.d(TAG, "Sincronización completada: $count récords sincronizados")
                }.onFailure { error ->
                    Log.e(TAG, "Error durante sincronización manual: ${error.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción durante syncNow: ${e.message}")
            }
        }
    }

    /**
     * Verifica el estado de la sincronización
     */
    fun checkSyncStatus() {
        scope.launch {
            try {
                Log.d(TAG, "Verificando estado de sincronización")
                val recordCount = syncRepository.getRecordCount()
                recordCount.onSuccess { count ->
                    Log.d(TAG, "Total de récords en sincronización: $count")
                }.onFailure { error ->
                    Log.e(TAG, "Error al verificar estado: ${error.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción durante checkSyncStatus: ${e.message}")
            }
        }
    }

    /**
     * Obtiene el estado de la conexión
     */
    fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(context)
    }

    /**
     * Obtiene el tipo de conexión actual
     */
    fun getConnectionType(): String {
        return NetworkUtil.getConnectionType(context)
    }
}

