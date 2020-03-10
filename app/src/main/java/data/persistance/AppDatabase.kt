package data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RestaurantDaoEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRestaurantDao(): RestaurantDao
}
