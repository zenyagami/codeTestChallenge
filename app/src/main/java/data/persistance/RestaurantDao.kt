package data.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurants")
    fun getFavorites(): LiveData<List<RestaurantDaoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(item: RestaurantDaoEntity)

    @Query("DELETE FROM restaurants WHERE name=:restaurantName")
    fun deleteFavorite(restaurantName: String)

}
