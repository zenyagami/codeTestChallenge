package data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import data.local.LocalRepositoryImpl
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayInputStream

class LocalRepositoryTest {
    private val gson: Gson = Gson()
    private val assetManager: AssetManager = mock()
    private val localRepository = LocalRepositoryImpl(gson, assetManager)

    @Test
    fun `should not crash with wrong json format`() {
        // given
        val inputStream = ByteArrayInputStream("test".toByteArray(Charsets.UTF_8))
        whenever(assetManager.open(any())).thenReturn(inputStream)

        // when
        val result = localRepository.getResponseFromJson().test()

        //then
        result.assertValue(emptyList())
    }

    @Test
    fun `should not carash with wrong json format`() {
        // given

        val inputStream =
            ByteArrayInputStream(getJsonResponseFromFile().toByteArray(Charsets.UTF_8))
        whenever(assetManager.open(any())).thenReturn(inputStream)

        // when
        val result = localRepository.getResponseFromJson().test()

        //then
        //TODO assertEquals for all elements but I'm doing only the first one
        Assert.assertEquals(result.values().first().first().name, "Tanoshii Sushi")
        Assert.assertEquals(result.values().first()[1].name, "Tandoori Express")
        Assert.assertEquals(result.values().first()[2].name, "Royal Thai")
    }

    private fun getJsonResponseFromFile(): String {
        return LocalRepositoryTest::class.java.classLoader!!.getResourceAsStream("response.json")
            .bufferedReader().use { it.readText() }
    }
}
