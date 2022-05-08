package com.jasonkhew96.wenku8.di

import android.content.Context
import androidx.room.Room
import com.jasonkhew96.wenku8.Constants.WENKU8_DATABASE
import com.jasonkhew96.wenku8.data.local.Wenku8Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Wenku8Database {
        return Room.databaseBuilder(
            context, Wenku8Database::class.java, WENKU8_DATABASE
        ).build()
    }
}

