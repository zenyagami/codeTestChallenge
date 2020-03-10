package app.di.modules

import android.content.Context
import android.content.res.AssetManager
import app.ui.MainApplication
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
}
