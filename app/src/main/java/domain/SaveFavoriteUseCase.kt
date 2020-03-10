package domain

import data.persistance.RestaurantDaoEntity
import data.persistance.RestaurantRepository
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {

    fun run(restaurantName: String) {
        //since the json proved does not contain any ID i will use the name as ID and unique
        val item = RestaurantDaoEntity(name = restaurantName)
        restaurantRepository.insertFavorite(item)
    }
}
