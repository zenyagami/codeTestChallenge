package data.persistance

import androidx.lifecycle.LiveData
import app.util.SchedulersProvider
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val schedulersProvider: SchedulersProvider
) : RestaurantRepository {

    override fun getFavorites(): LiveData<List<RestaurantDaoEntity>> = restaurantDao.getFavorites()

    override fun deleteFavorite(restaurantName: String) {
        runAsync { restaurantDao.deleteFavorite(restaurantName) }
    }

    override fun insertFavorite(restaurantName: RestaurantDaoEntity) {
        runAsync { restaurantDao.insertFavorite(restaurantName) }
    }

    private fun runAsync(callback: () -> Unit) {
        val worker = schedulersProvider.newThread().createWorker()
        worker.schedule {
            callback()
            worker.dispose()
        }
    }
}
