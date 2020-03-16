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
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) :
    ViewModel() {
    private val _navigationEvent = MutableLiveData<MainActivityNavigation>()

    // i use Transformations.map to not expose mutableLive data to the activity, so teh activity can't post any event
    val navigationEvent: LiveData<MainActivityNavigation> = Transformations.map(_navigationEvent) {
        it
    }
    val restaurantListLiveData = loadData()


    private fun loadData(): LiveData<List<RestaurantModel>> {
        return MediatorLiveData<List<RestaurantModel>>().apply {
            // we only need to sort the first time not every time that the data source change
            var shouldSort = true
            addSource(getRestaurantListUseCase.run()) { restaurantList ->
                addSource(getFavoritesUseCase.run()) { favorites ->
                    shouldSort = false
                    if (favorites.isEmpty()) {
                        value = restaurantList.toDefaultSorting()
                        return@addSource
                    }
                    // check the previous value to keep the current sorting without modifying the list
                    val sortedList = (if (value == null) {
                        restaurantList
                    } else value)?.map { restaurantItem ->
                        val isFavorite =
                            favorites.find { it.restaurantName == restaurantItem.name } != null
                        restaurantItem.copy(isFavorite = isFavorite)
                    }
                    //TODO use sorting in background or with coroutines
                    value = if (shouldSort) {
                        sortedList?.toDefaultSorting()
                    } else {
                        sortedList
                    }
                }
            }
        }
    }

    private fun List<RestaurantModel>.toDefaultSorting(): List<RestaurantModel> {
        return this.sortedWith(
            compareBy({ it.isFavorite },
                { it.status == "open" },
                { it.status == "order ahead" },
                { it.status == "closed" })
        ).reversed()
    }

    fun onFavoriteClick(favorite: RestaurantModel) {
        if (favorite.isFavorite) {
            deleteFavoriteUseCase.run(favorite.name)
        } else {
            saveFavoriteUseCase.run(favorite.name)
        }

    }

    fun onSortButtonClicked() {
        _navigationEvent.postValue(MainActivityNavigation.OnSortCLickEvent)
    }

}
