package app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import app.ui.model.MainActivityNavigation
import domain.DeleteFavoriteUseCase
import domain.GetFavoritesUseCase
import domain.GetRestaurantListUseCase
import domain.SaveFavoriteUseCase
import domain.model.RestaurantModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) :
    ViewModel() {
    private val _navigationEvent = MutableLiveData<MainActivityNavigation>()
    private val compositeDisposable = CompositeDisposable()

    // i use Transformations.map to not expose mutableLive data to the activity, so teh activity can't post any event
    val navigationEvent: LiveData<MainActivityNavigation> = Transformations.map(_navigationEvent) {
        it
    }
    val restaurantListLiveData = loadData()


    private fun loadData(): LiveData<List<RestaurantModel>> {
        return MediatorLiveData<List<RestaurantModel>>().apply {
            addSource(getRestaurantListUseCase.run()) { restaurantList ->
                value = restaurantList
                addSource(getFavoritesUseCase.run()) { favorites ->
                    if (favorites.isEmpty()) {
                        return@addSource
                    }
                    value = restaurantList.map { restaurantItem ->
                        val isFavorite =
                            favorites.find { it.restaurantName == restaurantItem.name } != null
                        if (isFavorite) {
                            restaurantItem.copy(isFavorite = true)
                        } else {
                            restaurantItem
                        }
                    }
                }
            }
        }


    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onFavoriteClick(favorite: RestaurantModel) {
        if (favorite.isFavorite) {
            deleteFavoriteUseCase.run(favorite.name)
        } else {
            saveFavoriteUseCase.run(favorite.name)
        }

    }

}
