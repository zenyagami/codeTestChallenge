package app.di.modules

import app.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

// I could create several modules depending on the different features, but for now I will keep it in one file
@Module
abstract class ModuleInjectorUi {

    @ContributesAndroidInjector
    abstract fun bindsMainActivity(): MainActivity

}
