package com.gato.movieapp.di

import android.content.Context
import androidx.room.Room
import com.gato.movieapp.data.database.MoviesDatabase
import com.gato.movieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MoviesDatabase::class.java, Constants.DB_NAME).build()

    @Singleton
    @Provides
    fun provideMoviesDao(database: MoviesDatabase) = database.moviesDao()

    @Singleton
    @Provides
    fun provideTvDao(database: MoviesDatabase) = database.tvDao()
}