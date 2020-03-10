package domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import data.local.LocalRepository
import domain.model.RestaurantModel
import io.reactivex.Observable
import javax.inject.Inject

class GetRestaurantListUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    fun run(): LiveData<List<RestaurantModel>> {
        return localRepository.getResponseFromJson()
            .flatMapObservable { Observable.fromIterable(it) }
            .map {
                RestaurantModel(
                    name = it.name,
                    distance = it.sortingValues.distance,
                    averageProductPrice = it.sortingValues.averageProductPrice,
                    bestMatch = it.sortingValues.bestMatch,
                    deliveryCosts = it.sortingValues.deliveryCosts,
                    minCost = it.sortingValues.minCost,
                    newest = it.sortingValues.newest,
                    popularity = it.sortingValues.popularity,
                    ratingAverage = it.sortingValues.ratingAverage,
                    status = it.status
                )
            }.toList()
            .toFlowable()
            .toLiveData()

    }
}
