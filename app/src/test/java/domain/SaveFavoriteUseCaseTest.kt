package domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import data.persistance.RestaurantDaoEntity
import data.persistance.RestaurantRepository
import org.junit.Test

class SaveFavoriteUseCaseTest {
    private val restaurantRepository: RestaurantRepository = mock()
    private val useCase = SaveFavoriteUseCase(restaurantRepository)

    @Test
    fun `should insert entity into database`() {
        //given
        val restaurantName = "habba habba"
        val entity = RestaurantDaoEntity(name = restaurantName)

        // when
        useCase.run(restaurantName)

        verify(restaurantRepository).insertFavorite(entity)
    }
}
