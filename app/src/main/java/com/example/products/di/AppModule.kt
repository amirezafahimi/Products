package com.example.products.di

import android.content.Context
import androidx.room.Room
import com.example.findpix.data.source.local.AppDao
import com.example.products.MyApplication
import com.example.products.data.source.local.Database
import com.example.products.data.source.local.LocalDataSource
import com.example.products.data.source.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication = app as MyApplication

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideRoomAppDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java, "Products.db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAppDao(database: Database): AppDao {
        return database.appDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}

