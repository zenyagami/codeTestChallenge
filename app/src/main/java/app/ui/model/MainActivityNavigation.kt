package app.ui.model

import androidx.annotation.StringRes

sealed class MainActivityNavigation {
    object OnSortCLickEvent : MainActivityNavigation()
    data class OnSortChanged(@StringRes val sortResourceId: Int) : MainActivityNavigation()
}
