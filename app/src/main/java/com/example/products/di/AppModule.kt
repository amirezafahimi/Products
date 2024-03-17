package com.example.products.di

import android.content.Context
import androidx.room.Room
import com.example.products.MyApplication
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
}
