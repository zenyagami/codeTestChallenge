package domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import data.persistance.RestaurantRepository
import org.junit.Test

class DeleteFavoriteUseCaseTest {
    private val restaurantRepository: RestaurantRepository = mock()
    private val useCase = DeleteFavoriteUseCase(restaurantRepository)

    @Test
    fun a() {
        //given
        val restaurantName = "habba habba"

        // when
        useCase.run(restaurantName)

        // then
        verify(restaurantRepository).deleteFavorite(restaurantName)
    }
}
