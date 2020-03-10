package app.di

import app.di.modules.ApplicationModule
import app.di.modules.ModuleInjectorUi
import app.di.modules.ModuleViewModel
import app.ui.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import data.di.ModuleLocalRepository
import data.persistance.di.ModuleDatabase
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ModuleViewModel::class,
        ModuleLocalRepository::class,
        ModuleDatabase::class,
        ModuleInjectorUi::class]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)

}
