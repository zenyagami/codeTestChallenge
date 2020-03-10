package data.persistance.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import data.persistance.AppDatabase
import data.persistance.RestaurantDao
import data.persistance.RestaurantRepository
import data.persistance.RestaurantRepositoryImpl
import java.util.concurrent.Executors
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [BindingsDatabase::class])
class ModuleDatabase {

    @DB
    @Provides
    @Singleton
    fun appDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .setQueryExecutor(Executors.newFixedThreadPool(1))
            .setTransactionExecutor(Executors.newFixedThreadPool(1))
            .build()
    }

    @Provides
    fun providesAppDrawer(@DB appDatabase: AppDatabase): RestaurantDao {
        return appDatabase.getRestaurantDao()
    }


}

@Module
abstract class BindingsDatabase() {
    @Binds
    @Singleton
    abstract fun bindsRestaurantRepository(restaurantRepositoryImpl: RestaurantRepositoryImpl): RestaurantRepository
}

// Use this @Qualifier to avoid the database being injected , so the developer must always get the specific table
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
private annotation class DB
