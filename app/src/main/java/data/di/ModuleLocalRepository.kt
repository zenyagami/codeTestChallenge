package data.di

import dagger.Binds
import dagger.Module
import data.local.LocalRepository
import data.local.LocalRepositoryImpl
import javax.inject.Singleton

@Module
abstract class ModuleLocalRepository {

    @Binds
    @Singleton
    abstract fun bindsLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

}
