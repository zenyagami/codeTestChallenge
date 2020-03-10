package data.local

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.entities.ResponseDto
import data.entities.RestaurantDto
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val assetManager: AssetManager
) : LocalRepository {
    override fun getResponseFromJson(): Single<List<RestaurantDto>> {
        return Single.just(getDatFromJson())
    }


    private fun getDatFromJson(): List<RestaurantDto> {
        return try {
            val jsonString =
                assetManager.open(LOCAL_RESPONSE_FILE).bufferedReader().use { it.readText() }
            gson.fromJson<ResponseDto>(jsonString, object : TypeToken<ResponseDto>() {}.type)
                .restaurants
        } catch (exception: Exception) {
            Timber.d(exception, "Error loading response data")
            //todo instead empty list use a better error handling like Arrow
            emptyList()
        }


    }

    companion object {
        private const val LOCAL_RESPONSE_FILE = "response.json"
    }

}
