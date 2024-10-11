package cl.bootcamp.mobistore.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.room.PhoneDao

@Database(
    entities = [Phone::class],
    version = 1
)

abstract class DbDataSource: RoomDatabase() {
    abstract fun phoneDao(): PhoneDao
}