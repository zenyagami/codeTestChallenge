package domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import data.entities.RestaurantDto
import data.entities.SortingDto
import data.local.LocalRepository
import getOrAwaitValue
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetRestaurantListUseCaseTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val localRepository: LocalRepository = mock()
    private val useCase = GetRestaurantListUseCase(localRepository)

    @Test
    fun `should map data correctly`() {
        // given
        val baseDto = RestaurantDto(
            name = "habba",
            status = "Open",
            sortingValues = SortingDto(
                bestMatch = 23.0,
                ratingAverage = 32.2,
                popularity = 2.2,
                newest = 12.2,
                minCost = 110,
                distance = 1000,
                deliveryCosts = 12,
                averageProductPrice = 10
            )
        )
        val list = listOf(
            baseDto,
            baseDto.copy(name = "another restaurant"),
            baseDto.copy(name = "test")
        )
        whenever(localRepository.getResponseFromJson()).thenReturn(Single.just(list))

        // when
        val result = useCase.run().getOrAwaitValue()

        // then

        list.forEachIndexed { index, restaurantDto ->
            Assert.assertEquals(result[index].name, restaurantDto.name)
            Assert.assertEquals(result[index].minCost, restaurantDto.sortingValues.minCost)
            Assert.assertEquals(
                result[index].averageProductPrice,
                restaurantDto.sortingValues.averageProductPrice
            )
            Assert.assertEquals(result[index].bestMatch, restaurantDto.sortingValues.bestMatch, 0.0)
            Assert.assertEquals(
                result[index].deliveryCosts,
                restaurantDto.sortingValues.deliveryCosts
            )
            Assert.assertEquals(result[index].distance, restaurantDto.sortingValues.distance)
            Assert.assertEquals(result[index].newest, restaurantDto.sortingValues.newest, 0.0)
            Assert.assertEquals(
                result[index].popularity,
                restaurantDto.sortingValues.popularity,
                0.0
            )
            Assert.assertEquals(
                result[index].ratingAverage,
                restaurantDto.sortingValues.ratingAverage,
                0.0
            )
            Assert.assertEquals(result[index].status, restaurantDto.status)
        }

    }

}
