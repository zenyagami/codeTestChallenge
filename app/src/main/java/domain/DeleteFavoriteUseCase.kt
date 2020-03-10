package domain

import data.persistance.RestaurantRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {

    fun run(restaurantName: String) = restaurantRepository.deleteFavorite(restaurantName)
}
