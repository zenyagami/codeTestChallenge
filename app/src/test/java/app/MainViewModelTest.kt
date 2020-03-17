package app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.ui.model.MainActivityNavigation
import app.ui.viewmodel.MainViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import domain.DeleteFavoriteUseCase
import domain.GetFavoritesUseCase
import domain.GetRestaurantListUseCase
import domain.SaveFavoriteUseCase
import domain.model.RestaurantModel
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify

class MainViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val getRestaurantListUseCase: GetRestaurantListUseCase = mock()
    private val getFavoritesUseCase: GetFavoritesUseCase = mock()
    private val saveFavoriteUseCase: SaveFavoriteUseCase = mock()
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = mock()

    private val viewModel = MainViewModel(
        getRestaurantListUseCase,
        getFavoritesUseCase,
        saveFavoriteUseCase,
        deleteFavoriteUseCase
    )

    @Test
    fun `should call delete favorite event`() {
        // given
        val restaurantModel = RestaurantModel(
            "My restaurant",
            "status",
            0.4,
            0.3,
            23.0,
            100,
            3.0,
            103,
            12,
            4,
            true
        )

        //when
        viewModel.onFavoriteClick(restaurantModel)

        // then
        verify(deleteFavoriteUseCase).run(restaurantModel.name)
    }

    @Test
    fun `should not call delete favorite event`() {
        // given
        val restaurantModel = RestaurantModel(
            "My restaurant",
            "status",
            0.4,
            0.3,
            23.0,
            100,
            3.0,
            103,
            12,
            4,
            false
        )

        //when
        viewModel.onFavoriteClick(restaurantModel)

        // then
        verify(deleteFavoriteUseCase, never()).run(restaurantModel.name)
    }

    @Test
    fun `should call save favorite event`() {
        // given
        val restaurantModel = RestaurantModel(
            "My restaurant",
            "status",
            0.4,
            0.3,
            23.0,
            100,
            3.0,
            103,
            12,
            4,
            false
        )

        //when
        viewModel.onFavoriteClick(restaurantModel)

        // then
        verify(saveFavoriteUseCase).run(restaurantModel.name)
    }


    @Test
    fun `should Not call save favorite event`() {
        // given
        val restaurantModel = RestaurantModel(
            "My restaurant",
            "status",
            0.4,
            0.3,
            23.0,
            100,
            3.0,
            103,
            12,
            4,
            true
        )

        //when
        viewModel.onFavoriteClick(restaurantModel)

        // then
        verify(saveFavoriteUseCase, never()).run(restaurantModel.name)
    }

    @Test
    fun `should call OnSortCLickEvent`() {
        // given
        val observer: Observer<MainActivityNavigation> = mock()
        viewModel.navigationEvent.observeForever(observer)

        //when
        viewModel.onSortButtonClicked()

        //
        verify(observer).onChanged(MainActivityNavigation.OnSortCLickEvent)
    }
}
