package gz.dam.trabajosimondize.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import gz.dam.trabajosimondize.data.record.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}