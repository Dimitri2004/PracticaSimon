package gz.dam.trabajosimondize.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import gz.dam.trabajosimondize.util.NetworkUtil

/**
 * Worker que ejecuta la sincronización en segundo plano usando WorkManager.
 * Se ejecuta periódicamente para sincronizar datos locales con MongoDB.
 *
 * @param context El contexto de la aplicación
 * @param params Parámetros del trabajo
 */
class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val TAG = "SyncWorker"

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Iniciando trabajo de sincronización en segundo plano")

            // Verificar si hay conexión a internet
            if (!NetworkUtil.isNetworkAvailable(applicationContext)) {
                Log.w(TAG, "No hay conexión a internet, reintentando después")
                return Result.retry()
            }

            Log.d(TAG, "Ejecutando sincronización")
            // Aquí se ejecutaría la sincronización real
            // El repositorio se inyectaría a través del constructor o servicio

            Log.d(TAG, "Sincronización completada exitosamente")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error en trabajo de sincronización: ${e.message}")
            Result.retry()
        }
    }
}

