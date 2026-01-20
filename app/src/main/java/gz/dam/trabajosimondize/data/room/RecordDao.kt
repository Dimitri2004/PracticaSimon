package gz.dam.trabajosimondize.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gz.dam.trabajosimondize.data.Utility.RecordEntity

@Dao
interface RecordDao {

    @Query("SELECT * FROM RecordEntity")
    fun getAll(): List<RecordEntity>

    @Query("SELECT * FROM RecordEntity WHERE id IN (:RecordIds)")
    fun loadAllByIds(RecordIds: IntArray): List<RecordEntity>

    @Query("SELECT * FROM RecordEntity WHERE id = :id")
    fun getRecordById(id: Int): RecordEntity?

    @Query("SELECT * FROM RecordEntity ORDER BY Puntuación DESC LIMIT 1")
    fun getMaxRecord(): RecordEntity?

    @Query("SELECT COUNT(*) FROM RecordEntity")
    fun getCount(): Int

    @Insert
    fun insertAll(vararg Records: RecordEntity)

    @Insert
    suspend fun insert(record: RecordEntity): Long

    @Update
    suspend fun update(record: RecordEntity)

    @Delete
    fun delete(RecordEntity: RecordEntity)

    @Query("DELETE FROM RecordEntity")
    suspend fun deleteAll()
}