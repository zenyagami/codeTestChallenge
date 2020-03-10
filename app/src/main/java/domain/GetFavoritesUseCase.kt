package domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import data.persistance.RestaurantRepository
import domain.model.FavoritesModel
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {

    fun run(): LiveData<List<FavoritesModel>> {
        return Transformations.map(restaurantRepository.getFavorites()) { list ->
            list.map {
                //only map the data we need without exposing all the items to other layers
                FavoritesModel(it.name)
            }
        }

    }
}
