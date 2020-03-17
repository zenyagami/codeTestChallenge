package app.di.modules

import android.content.Context
import android.content.res.AssetManager
import app.ui.MainApplication
import app.util.AppSchedulersProvider
import app.util.SchedulersProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesAssetManager(context: Context): AssetManager {
        return context.assets
    }

    @Provides
    fun providesAppSchedulersProvider(): SchedulersProvider = AppSchedulersProvider()
}
