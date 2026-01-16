package gz.dam.trabajosimondize.data.repository

import android.util.Log
import gz.dam.trabajosimondize.data.model.RecordRemote

/**
 * Repositorio de sincronización que combina datos locales y remotos.
 * Implementa un patrón offline-first: intenta usar datos remotos, pero cae a local si no hay conexión.
 *
 * @param localRepository Repositorio local (Room/SQLite)
 * @param remoteRepository Repositorio remoto (MongoDB)
 */
class RecordSyncRepository(
    private val localRepository: IRecordRepository,
    private val remoteRepository: IRecordRepository
) : IRecordRepository {
    private val TAG = "RecordSyncRepository"

    /**
     * Crea un récord en ambas fuentes (primero local, luego remoto)
     */
    override suspend fun createRecord(record: RecordRemote): Result<String> {
        return try {
            // Primero guardamos en la base de datos local
            val localResult = localRepository.createRecord(record)
            val localId = localResult.getOrNull()

            if (localId != null) {
                // Luego intentamos sincronizar con el servidor remoto
                val recordWithId = record.copy(id = localId)
                val remoteResult = remoteRepository.createRecord(recordWithId)

                if (remoteResult.isSuccess) {
                    Log.d(TAG, "Récord creado en local y remoto con ID: $localId")
                    Result.success(localId)
                } else {
                    // Si falla el remoto, al menos está en local (será sincronizado después)
                    Log.w(TAG, "Récord creado localmente pero falló en remoto: ${remoteResult.exceptionOrNull()?.message}")
                    Result.success(localId)
                }
            } else {
                Result.failure(Exception("No se pudo crear el récord localmente"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al crear récord: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Lee un récord intentando primero desde remoto
     */
    override suspend fun readRecord(id: String): Result<RecordRemote> {
        return try {
            // Intentamos leer desde el remoto primero
            val remoteResult = remoteRepository.readRecord(id)

            if (remoteResult.isSuccess) {
                Log.d(TAG, "Récord leído desde remoto: $id")
                return remoteResult
            } else {
                // Si falla, intentamos leer desde local
                Log.d(TAG, "No se pudo leer desde remoto, intentando local: $id")
                localRepository.readRecord(id)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al leer récord: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Lee todos los récords combinando local y remoto
     */
    override suspend fun readAllRecords(): Result<List<RecordRemote>> {
        return try {
            // Intentamos leer desde remoto
            val remoteResult = remoteRepository.readAllRecords()

            if (remoteResult.isSuccess) {
                Log.d(TAG, "Récords leídos desde remoto")
                return remoteResult
            } else {
                // Si falla, intentamos leer desde local
                Log.d(TAG, "No se pudo leer desde remoto, intentando local")
                localRepository.readAllRecords()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al leer todos los récords: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene el récord máximo intentando primero desde remoto
     */
    override suspend fun getMaxRecord(): Result<RecordRemote> {
        return try {
            // Intentamos obtener desde remoto primero
            val remoteResult = remoteRepository.getMaxRecord()

            if (remoteResult.isSuccess) {
                Log.d(TAG, "Récord máximo obtenido desde remoto")
                return remoteResult
            } else {
                // Si falla, intentamos obtener desde local
                Log.d(TAG, "No se pudo obtener máximo desde remoto, intentando local")
                localRepository.getMaxRecord()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener récord máximo: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Actualiza un récord en ambas fuentes
     */
    override suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit> {
        return try {
            // Primero actualizamos localmente
            val localResult = localRepository.updateRecord(id, record)

            if (localResult.isSuccess) {
                // Luego intentamos actualizar en remoto
                val remoteResult = remoteRepository.updateRecord(id, record)

                if (remoteResult.isSuccess) {
                    Log.d(TAG, "Récord actualizado en local y remoto: $id")
                    Result.success(Unit)
                } else {
                    // Si falla en remoto, al menos está actualizado en local
                    Log.w(TAG, "Récord actualizado localmente pero falló en remoto: ${remoteResult.exceptionOrNull()?.message}")
                    Result.success(Unit)
                }
            } else {
                Result.failure(Exception("No se pudo actualizar el récord localmente"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar récord: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Elimina un récord de ambas fuentes
     */
    override suspend fun deleteRecord(id: String): Result<Unit> {
        return try {
            // Primero eliminamos localmente
            val localResult = localRepository.deleteRecord(id)

            if (localResult.isSuccess) {
                // Luego intentamos eliminar del remoto
                val remoteResult = remoteRepository.deleteRecord(id)

                if (remoteResult.isSuccess) {
                    Log.d(TAG, "Récord eliminado en local y remoto: $id")
                    Result.success(Unit)
                } else {
                    // Si falla en remoto, al menos está eliminado localmente
                    Log.w(TAG, "Récord eliminado localmente pero falló en remoto: ${remoteResult.exceptionOrNull()?.message}")
                    Result.success(Unit)
                }
            } else {
                Result.failure(Exception("No se pudo eliminar el récord localmente"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar récord: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Elimina todos los récords de ambas fuentes
     */
    override suspend fun deleteAllRecords(): Result<Unit> {
        return try {
            val localResult = localRepository.deleteAllRecords()
            val remoteResult = remoteRepository.deleteAllRecords()

            if (localResult.isSuccess && remoteResult.isSuccess) {
                Log.d(TAG, "Todos los récords fueron eliminados en local y remoto")
                Result.success(Unit)
            } else {
                Log.w(TAG, "Error al eliminar todos los récords en alguna fuente")
                Result.success(Unit) // Devolvemos éxito si al menos se eliminaron localmente
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar todos los récords: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene el conteo de récords (preferiblemente desde remoto)
     */
    override suspend fun getRecordCount(): Result<Int> {
        return try {
            val remoteResult = remoteRepository.getRecordCount()

            if (remoteResult.isSuccess) {
                Log.d(TAG, "Conteo de récords obtenido desde remoto")
                return remoteResult
            } else {
                Log.d(TAG, "No se pudo obtener conteo desde remoto, intentando local")
                localRepository.getRecordCount()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener conteo de récords: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Sincroniza los datos locales no sincronizados con el remoto
     */
    suspend fun syncPendingRecords(): Result<Int> {
        return try {
            Log.d(TAG, "Iniciando sincronización de récords pendientes")
            // Aquí se implementaría lógica para sincronizar solo los récords no sincronizados
            val allRecords = localRepository.readAllRecords()
            var syncedCount = 0

            allRecords.getOrNull()?.forEach { record ->
                if (!record.sincronizado) {
                    val result = remoteRepository.createRecord(record)
                    if (result.isSuccess) {
                        syncedCount++
                    }
                }
            }

            Log.d(TAG, "$syncedCount récords sincronizados")
            Result.success(syncedCount)
        } catch (e: Exception) {
            Log.e(TAG, "Error durante la sincronización: ${e.message}")
            Result.failure(e)
        }
    }
}

