package app.ui.model

import domain.model.RestaurantModel

sealed class MainActivityNavigation {
    data class UpdateDataSetEvent(val list: List<RestaurantModel>) : MainActivityNavigation()
}
