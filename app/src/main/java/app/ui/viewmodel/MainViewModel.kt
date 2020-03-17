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
    private val sortLiveData = MutableLiveData<List<RestaurantModel>>()
    private var currentSortOption = 0
    private var shouldSort = true

    // i use Transformations.map to not expose mutableLive data to the activity, so teh activity can't post any event
    val navigationEvent: LiveData<MainActivityNavigation> = Transformations.map(_navigationEvent) {
        it
    }
    val restaurantListLiveData = loadData()

    private fun loadData(): LiveData<List<RestaurantModel>> {
        return MediatorLiveData<List<RestaurantModel>>().apply {
            // we only need to sort the first time not every time that the data source change

            addSource(getRestaurantListUseCase.run()) { restaurantList ->
                addSource(getFavoritesUseCase.run()) { favorites ->
                    if (favorites.isEmpty()) {
                        shouldSort = false
                        value = restaurantList.toSortingList()
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
                        shouldSort = false
                        sortedList?.toSortingList()
                    } else {
                        sortedList
                    }
                }
                addSource(sortLiveData){
                    value = it
                }
            }
        }
    }

    private fun List<RestaurantModel>.toSortingList(): List<RestaurantModel> {
        return this.sortedWith(getDefaultComparator()).reversed()
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

    fun setCurrentSortOption(option: Int) {
        shouldSort = true
        currentSortOption = option
    }

    private fun getDefaultComparator(): Comparator<RestaurantModel> {
        return compareBy<RestaurantModel> { it.isFavorite }
            .thenBy { it.status == "open" }
            .thenBy { it.status == "order ahead" }
            .thenBy { it.status == "closed" }

    }

    fun getSelectedSortOption(): Int = currentSortOption

    private fun getSort(): Comparator<RestaurantModel> {
        return SortOptions.values()
            .associateBy(SortOptions::ordinal)[currentSortOption]?.let { sortOption ->
            compareBy<RestaurantModel> { it.isFavorite }
                .thenBy { it.status == "open" }
                .thenBy { it.status == "order ahead" }
                .thenBy { it.status == "close" }
                .apply {
                    when (sortOption) {
                        SortOptions.BEST_MATCH -> thenBy { it.bestMatch }
                        SortOptions.AVERAGE_PRODUCT_PRICE -> thenByDescending { it.averageProductPrice }
                        SortOptions.DELIVERY_COST -> thenByDescending { it.deliveryCosts }
                        SortOptions.DISTANCE -> thenByDescending { it.distance  }
                        SortOptions.MINIMUM_COST -> thenByDescending { it.minCost }
                        SortOptions.NEWEST_ITEM -> thenByDescending { it.newest }
                        SortOptions.POPULARITY -> thenBy { it.popularity }
                        SortOptions.RATING_AVERAGE -> thenByDescending { it.averageProductPrice }
                    }
                }
        } ?: getDefaultComparator()
    }

    fun applySorting() {
        //I'm using cache to avoid call the "API/JSON" everytime the user change sorting and save data/calls, this can be changed easily to call the api tho
        restaurantListLiveData.value?.let {
            sortLiveData.postValue(it.sortedWith(getSort()).reversed())
        }
    }

    //TODO use strings map or something better
    private enum class SortOptions {
        BEST_MATCH,
        NEWEST_ITEM,
        RATING_AVERAGE,
        DISTANCE,
        POPULARITY,
        AVERAGE_PRODUCT_PRICE,
        DELIVERY_COST,
        MINIMUM_COST
    }
}
