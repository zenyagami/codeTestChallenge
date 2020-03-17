package app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.toLiveData
import app.ui.model.MainActivityNavigation
import app.ui.viewmodel.MainViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.whenever
import domain.DeleteFavoriteUseCase
import domain.GetFavoritesUseCase
import domain.GetRestaurantListUseCase
import domain.SaveFavoriteUseCase
import domain.model.RestaurantModel
import io.reactivex.Flowable
import org.junit.Ignore
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
    @Ignore
    fun `should map correctoly`() {
        // given
        val list = Flowable.just(getMockedList()).toLiveData()
        val liveData = MutableLiveData<List<RestaurantModel>>()

        val observer: Observer<List<RestaurantModel>> = mock()
        whenever(getRestaurantListUseCase.run()).thenReturn(liveData)

        // when
        val obs = viewModel.restaurantListLiveData.observeForever(observer)

        // then
        val sorterList = list.value!!.sortedWith(compareBy<RestaurantModel> { it.isFavorite }
            .thenBy { it.status == "open" }
            .thenBy { it.status == "order ahead" }
            .thenBy { it.status == "closed" })

        verify(observer).onChanged(sorterList)

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


    private fun getMockedList(): List<RestaurantModel> {
        return listOf(
            RestaurantModel(
                name = "habba habba",
                status = "open",
                isFavorite = false,
                averageProductPrice = 2,
                bestMatch = 3.3,
                deliveryCosts = 12,
                distance = 100,
                minCost = 12,
                newest = 1.1,
                popularity = 23.3,
                ratingAverage = 2.3
            ),
            RestaurantModel(
                name = "habba habba",
                status = "open",
                isFavorite = false,
                averageProductPrice = 2,
                bestMatch = 5.2,
                deliveryCosts = 12,
                distance = 1030,
                minCost = 20,
                newest = 1.3,
                popularity = 10.3,
                ratingAverage = 2.3
            ),
            RestaurantModel(
                name = "coffee",
                status = "open",
                isFavorite = false,
                averageProductPrice = 2,
                bestMatch = 3.3,
                deliveryCosts = 12,
                distance = 1040,
                minCost = 12,
                newest = 1.2,
                popularity = 23.3,
                ratingAverage = 2.3
            ),
            RestaurantModel(
                name = "Starbucks",
                status = "open",
                isFavorite = false,
                averageProductPrice = 2,
                bestMatch = 2.2,
                deliveryCosts = 12,
                distance = 1005,
                minCost = 12,
                newest = 1.1,
                popularity = 25.3,
                ratingAverage = 2.3
            )
        )
    }


}
