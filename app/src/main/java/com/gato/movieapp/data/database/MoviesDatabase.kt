package com.gato.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gato.movieapp.data.database.dao.MoviesDao
import com.gato.movieapp.data.database.dao.RemoteKeysDao
import com.gato.movieapp.data.database.dao.TvDao
import com.gato.movieapp.data.database.entities.movie.*
import com.gato.movieapp.data.database.entities.tv.*


@Database(
    entities = [
        FavoriteMovieEntity::class,
        TrendingMovieEntity::class,
        PopularMovieEntity::class,
        TopRatedMovieEntity::class,
        TrendingSeeAllMovieEntity::class,
        PopularSeeAllMovieEntity::class,
        TopRatedSeeAllMovieEntity::class,
        TrendingMovieRemoteKeys::class,
        PopularMovieRemoteKeys::class,
        TopRatedMovieRemoteKeys::class,
        UpComingMovieEntity::class,
        TrendingTvEntity::class,
        PopularTvEntity::class,
        TopRatedTvEntity::class,
        FavoriteTvEntity::class,
        TrendingSeeAllTvEntity::class,
        PopularSeeAllTvEntity::class,
        TopRatedSeeAllTvEntity::class,
        TrendingTvRemoteKeys::class,
        PopularTvRemoteKeys::class,
        TopRatedTvRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(MoviesTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun tvDao(): TvDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}