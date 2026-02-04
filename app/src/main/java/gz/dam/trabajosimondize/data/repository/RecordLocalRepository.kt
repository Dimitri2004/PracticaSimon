package gz.dam.trabajosimondize.data.repository

import android.util.Log
import gz.dam.trabajosimondize.data.record.RecordRemote
import gz.dam.trabajosimondize.data.record.RecordEntity
import gz.dam.trabajosimondize.data.room.AppDatabase
import java.time.format.DateTimeFormatter

/**
 * Implementación del repositorio para acceso a datos locales (Room/SQLite).
 * Esta clase maneja todas las operaciones CRUD en la base de datos local.
 *
 * @param database Instancia de AppDatabase para acceder a los DAOs
 */
class RecordLocalRepository(private val database: AppDatabase) : IRecordRepository {
    private val TAG = "RecordLocalRepository"
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    private val recordDao = database.recordDao()

    /**
     * Crea un nuevo récord en la base de datos local
     */
    override suspend fun createRecord(record: RecordRemote): Result<String> {
        return try {
            val entity = RecordEntity(
                id = null,
                puntuacion = record.puntuacion,
                fecha = record.fecha
            )
            val id = recordDao.insert(entity)
            Log.d(TAG, "Récord creado localmente con ID: $id")
            Result.success(id.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Error al crear récord localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene un récord específico por ID
     */
    override suspend fun readRecord(id: String): Result<RecordRemote> {
        return try {
            val entity = recordDao.getRecordById(id.toInt())
            if (entity != null) {
                val record = entity.toRecordRemote()
                Log.d(TAG, "Récord leído localmente: ${record.puntuacion}")
                Result.success(record)
            } else {
                Result.failure(Exception("Récord no encontrado con ID: $id"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al leer récord localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene todos los récords de la base de datos local
     */
    override suspend fun readAllRecords(): Result<List<RecordRemote>> {
        return try {
            val entities = recordDao.getAll()
            val records = entities.map { it.toRecordRemote() }
            Log.d(TAG, "Se leyeron ${records.size} récords localmente")
            Result.success(records)
        } catch (e: Exception) {
            Log.e(TAG, "Error al leer todos los récords localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene el récord con la puntuación más alta
     */
    override suspend fun getMaxRecord(): Result<RecordRemote> {
        return try {
            val entity = recordDao.getMaxRecord()
            if (entity != null) {
                val record = entity.toRecordRemote()
                Log.d(TAG, "Récord máximo obtenido localmente: ${record.puntuacion}")
                Result.success(record)
            } else {
                Result.failure(Exception("No hay récords en la base de datos local"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener récord máximo localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Actualiza un récord existente en la base de datos local
     */
    override suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit> {
        return try {
            val entity = RecordEntity(
                id = id.toInt(),
                puntuacion = record.puntuacion,
                fecha = record.fecha
            )
            recordDao.update(entity)
            Log.d(TAG, "Récord actualizado localmente: $id")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar récord localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Elimina un récord específico de la base de datos local
     */
    override suspend fun deleteRecord(id: String): Result<Unit> {
        return try {
            val entity = recordDao.getRecordById(id.toInt())
            if (entity != null) {
                recordDao.delete(entity)
                Log.d(TAG, "Récord eliminado localmente: $id")
                Result.success(Unit)
            } else {
                Result.failure(Exception("Récord no encontrado para eliminar: $id"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar récord localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Elimina todos los récords de la base de datos local
     */
    override suspend fun deleteAllRecords(): Result<Unit> {
        return try {
            recordDao.deleteAll()
            Log.d(TAG, "Todos los récords fueron eliminados localmente")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar todos los récords localmente: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Obtiene el número total de récords en la base de datos local
     */
    override suspend fun getRecordCount(): Result<Int> {
        return try {
            val count = recordDao.getCount()
            Log.d(TAG, "Total de récords locales: $count")
            Result.success(count)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el conteo de récords: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Función auxiliar para convertir RecordEntity a RecordRemote
     */
    private fun RecordEntity.toRecordRemote(): RecordRemote {
        return RecordRemote(
            id = this.id.toString(),
            puntuacion = this.puntuacion ?: 0,
            fecha = this.fecha ?: "",
            sincronizado = true,
            fechaCreacion = System.currentTimeMillis(),
            fechaModificacion = System.currentTimeMillis()
        )
    }
}

