package data.persistance

import androidx.lifecycle.LiveData

interface RestaurantRepository {
    fun getFavorites(): LiveData<List<RestaurantDaoEntity>>

    fun deleteFavorite(restaurantName: String)

    fun insertFavorite(restaurantName: RestaurantDaoEntity)
}
