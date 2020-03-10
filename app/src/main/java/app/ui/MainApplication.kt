package app.ui

import android.app.Application
import app.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        Timber.plant(Timber.DebugTree())
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }


}
