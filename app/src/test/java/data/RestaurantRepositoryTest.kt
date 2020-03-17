package data

import TestSchedulerProvider
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import app.util.SchedulersProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import data.persistance.RestaurantDao
import data.persistance.RestaurantDaoEntity
import data.persistance.RestaurantRepositoryImpl
import getOrAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RestaurantRepositoryTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val restaurantDao: RestaurantDao = mock()
    private val schedulersProvider: SchedulersProvider = TestSchedulerProvider()
    private val repository = RestaurantRepositoryImpl(restaurantDao, schedulersProvider)

    @Test
    fun `should call delete favorite repository`() {
        // given
        val restaurantName = "habba habba"
        // when
        repository.deleteFavorite(restaurantName)

        // then
        verify(restaurantDao).deleteFavorite(restaurantName)
    }

    @Test
    fun `should call insert favorite repository`() {
        // given
        val entity = RestaurantDaoEntity(name = "habba habba")
        // when
        repository.insertFavorite(entity)

        // then
        verify(restaurantDao).insertFavorite(entity)
    }

    @Test
    fun `should call favorites repository`() {
        // given
        val liveData = MutableLiveData<List<RestaurantDaoEntity>>().apply {
            value = emptyList()
        }
        whenever(restaurantDao.getFavorites()).thenReturn(liveData)

        // when
        repository.getFavorites().getOrAwaitValue()

        // then
        verify(restaurantDao).getFavorites()
    }

    @Test
    fun `should map favorite correctly repository`() {
        // given
        val list = listOf(
            RestaurantDaoEntity(name = "restaurant"),
            RestaurantDaoEntity(name = "habba habba"),
            RestaurantDaoEntity(name = "Pizza")
        )

        val liveData = MutableLiveData<List<RestaurantDaoEntity>>().apply {
            value = list
        }
        whenever(restaurantDao.getFavorites()).thenReturn(liveData)

        // when
        val result = repository.getFavorites().getOrAwaitValue()

        // then
        //then
        list.forEachIndexed { index, restaurantDaoEntity ->
            Assert.assertEquals(result[index].name, restaurantDaoEntity.name)
        }
    }
}
