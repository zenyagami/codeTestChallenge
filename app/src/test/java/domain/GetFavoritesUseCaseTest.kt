package domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import data.persistance.RestaurantDaoEntity
import data.persistance.RestaurantRepository
import getOrAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetFavoritesUseCaseTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val restaurantRepository: RestaurantRepository = mock()
    private val useCase = GetFavoritesUseCase(restaurantRepository)

    @Test
    fun `should map the favorite model correctly`() {
        // given
        val list = listOf(
            RestaurantDaoEntity(name = "restaurant"),
            RestaurantDaoEntity(name = "habba habba"),
            RestaurantDaoEntity(name = "Pizza")
        )

        val liveData = MutableLiveData<List<RestaurantDaoEntity>>().apply {
            value = list
        }
        whenever(restaurantRepository.getFavorites()).thenReturn(liveData)

        // when
        val result = useCase.run().getOrAwaitValue()

        //then
        list.forEachIndexed { index, restaurantDaoEntity ->
            Assert.assertEquals(result[index].restaurantName, restaurantDaoEntity.name)
        }
    }
}
