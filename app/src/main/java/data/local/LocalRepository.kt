package data.local

import data.entities.RestaurantDto
import io.reactivex.Single

interface LocalRepository {
    fun getResponseFromJson(): Single<List<RestaurantDto>>
}
