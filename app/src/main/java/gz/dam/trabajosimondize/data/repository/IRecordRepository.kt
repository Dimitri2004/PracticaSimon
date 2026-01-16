package gz.dam.trabajosimondize.data.repository

import gz.dam.trabajosimondize.data.model.RecordRemote

/**
 * Interfaz que define el contrato para las operaciones CRUD de récords.
 * Esta interfaz puede ser implementada por diferentes fuentes de datos:
 * - Repositorio Local (Room/SQLite)
 * - Repositorio Remoto (MongoDB)
 * - Repositorio Sincronizado (combinación de ambos)
 */
interface IRecordRepository {

    /**
     * Crea un nuevo récord en la fuente de datos
     * @param record El objeto RecordRemote a crear
     * @return Result<String> con el ID del récord creado o un error
     */
    suspend fun createRecord(record: RecordRemote): Result<String>

    /**
     * Obtiene un récord específico por su ID
     * @param id El identificador único del récord
     * @return Result<RecordRemote> con el récord encontrado o un error
     */
    suspend fun readRecord(id: String): Result<RecordRemote>

    /**
     * Obtiene todos los récords disponibles
     * @return Result<List<RecordRemote>> con la lista de récords o un error
     */
    suspend fun readAllRecords(): Result<List<RecordRemote>>

    /**
     * Obtiene el récord con la puntuación más alta
     * @return Result<RecordRemote> con el récord máximo o un error
     */
    suspend fun getMaxRecord(): Result<RecordRemote>

    /**
     * Actualiza un récord existente
     * @param id El identificador único del récord a actualizar
     * @param record Los nuevos datos del récord
     * @return Result<Unit> indicando éxito o error
     */
    suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>

    /**
     * Elimina un récord de la fuente de datos
     * @param id El identificador único del récord a eliminar
     * @return Result<Unit> indicando éxito o error
     */
    suspend fun deleteRecord(id: String): Result<Unit>

    /**
     * Elimina todos los récords
     * @return Result<Unit> indicando éxito o error
     */
    suspend fun deleteAllRecords(): Result<Unit>

    /**
     * Obtiene el número total de récords
     * @return Result<Int> con el count de récords o un error
     */
    suspend fun getRecordCount(): Result<Int>
}

