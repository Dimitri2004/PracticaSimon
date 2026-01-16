package gz.dam.trabajosimondize.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import gz.dam.trabajosimondize.data.model.RecordRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/**
 * Implementación del repositorio para acceso a datos remotos (MongoDB).
 * Esta clase maneja todas las operaciones CRUD en la base de datos remota.
 *
 * @param mongoDbUri URI de conexión a MongoDB (incluyendo credenciales)
 * @param databaseName Nombre de la base de datos en MongoDB
 * @param collectionName Nombre de la colección de récords
 */
class RecordRemoteRepository(
    private val mongoDbUri: String,
    private val databaseName: String = "practicasimon",
    private val collectionName: String = "records"
) : IRecordRepository {
    private val TAG = "RecordRemoteRepository"
    private val gson = Gson()
    private val apiBaseUrl = "https://api.mongodb.com/api/atlas/v1.0" // URL base de Atlas API

    /**
     * Crea un nuevo récord en MongoDB
     */
    override suspend fun createRecord(record: RecordRemote): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(record)
                Log.d(TAG, "Creando récord remoto: $json")
                // Aquí se haría la llamada al API de MongoDB
                // Por ahora, devolvemos un ID simulado
                val simulatedId = "${System.currentTimeMillis()}_${(Math.random() * 10000).toInt()}"
                Result.success(simulatedId)
            } catch (e: Exception) {
                Log.e(TAG, "Error al crear récord remoto: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Obtiene un récord específico por ID desde MongoDB
     */
    override suspend fun readRecord(id: String): Result<RecordRemote> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Leyendo récord remoto con ID: $id")
                // Aquí se haría la llamada al API de MongoDB
                // Por ahora, devolvemos un error
                Result.failure(Exception("Récord no encontrado en servidor: $id"))
            } catch (e: Exception) {
                Log.e(TAG, "Error al leer récord remoto: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Obtiene todos los récords de MongoDB
     */
    override suspend fun readAllRecords(): Result<List<RecordRemote>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Leyendo todos los récords remotos")
                // Aquí se haría la llamada al API de MongoDB
                // Por ahora, devolvemos una lista vacía
                Result.success(emptyList())
            } catch (e: Exception) {
                Log.e(TAG, "Error al leer todos los récords remotos: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Obtiene el récord con la puntuación más alta desde MongoDB
     */
    override suspend fun getMaxRecord(): Result<RecordRemote> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Obteniendo récord máximo remoto")
                // Aquí se haría la llamada al API de MongoDB
                // Buscar el documento con mayor puntuación
                Result.failure(Exception("No hay récords en el servidor"))
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener récord máximo remoto: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Actualiza un récord existente en MongoDB
     */
    override suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(record)
                Log.d(TAG, "Actualizando récord remoto $id: $json")
                // Aquí se haría la llamada al API de MongoDB
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Error al actualizar récord remoto: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Elimina un récord específico de MongoDB
     */
    override suspend fun deleteRecord(id: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Eliminando récord remoto: $id")
                // Aquí se haría la llamada al API de MongoDB
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar récord remoto: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Elimina todos los récords de MongoDB
     */
    override suspend fun deleteAllRecords(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Eliminando todos los récords remotos")
                // Aquí se haría la llamada al API de MongoDB
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar todos los récords remotos: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Obtiene el número total de récords en MongoDB
     */
    override suspend fun getRecordCount(): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Obteniendo conteo de récords remotos")
                // Aquí se haría la llamada al API de MongoDB
                Result.success(0)
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener conteo de récords remotos: ${e.message}")
                Result.failure(e)
            }
        }
    }

    /**
     * Realiza una llamada HTTP al API de MongoDB
     */
    private fun makeHttpRequest(
        method: String,
        endpoint: String,
        body: String? = null
    ): String {
        val url = URL("$apiBaseUrl$endpoint")
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = method
            connection.setRequestProperty("Content-Type", "application/json")
            // Aquí se agregaría la autenticación (API Key)

            if (body != null && method in listOf("POST", "PUT", "PATCH")) {
                connection.doOutput = true
                connection.outputStream.write(body.toByteArray())
            }

            val responseCode = connection.responseCode
            val responseStream = if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            return responseStream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }
}

