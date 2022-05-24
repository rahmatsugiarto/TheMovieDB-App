package com.gato.movieapp.data.database

import com.gato.movieapp.data.database.dao.MoviesDao
import com.gato.movieapp.data.database.dao.TvDao
import com.gato.movieapp.data.database.entities.movie.*
import com.gato.movieapp.data.database.entities.tv.FavoriteTvEntity
import com.gato.movieapp.data.database.entities.tv.PopularTvEntity
import com.gato.movieapp.data.database.entities.tv.TopRatedTvEntity
import com.gato.movieapp.data.database.entities.tv.TrendingTvEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val tvDao: TvDao
) {

    fun readTrendingMovies(): Flow<List<TrendingMovieEntity>> = moviesDao.readTrending()
    fun readPopularMovies(): Flow<List<PopularMovieEntity>> = moviesDao.readPopular()
    fun readTopRatedMovies(): Flow<List<TopRatedMovieEntity>> = moviesDao.readTopRated()
    fun readFavoriteMovies(): Flow<List<FavoriteMovieEntity>> = moviesDao.readFavoriteMovie()
    fun readUpComingMovies(): Flow<List<UpComingMovieEntity>> = moviesDao.readUpComing()

    fun readTrendingTv(): Flow<List<TrendingTvEntity>> = tvDao.readTrending()
    fun readPopularTv(): Flow<List<PopularTvEntity>> = tvDao.readPopular()
    fun readTopRatedTv(): Flow<List<TopRatedTvEntity>> = tvDao.readTopRated()
    fun readFavoriteTv(): Flow<List<FavoriteTvEntity>> = tvDao.readFavoriteTv()

    suspend fun insertTrendingMovies(trendingMovieEntity: TrendingMovieEntity) {
        moviesDao.insertTrending(trendingMovieEntity)
    }

    suspend fun insertPopularMovies(popularMovieEntity: PopularMovieEntity) {
        moviesDao.insertPopular(popularMovieEntity)
    }

    suspend fun insertTopRatedMovies(topRatedMovieEntity: TopRatedMovieEntity) {
        moviesDao.insertTopRatedMovies(topRatedMovieEntity)
    }

    suspend fun insertFavoriteMovies(favoriteEntity: FavoriteMovieEntity) {
        moviesDao.insertFavoriteMovies(favoriteEntity)
    }

    suspend fun insertUpComingMovies(upComingMovieEntity: UpComingMovieEntity) {
        moviesDao.insertUpComing(upComingMovieEntity)
    }


    suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        moviesDao.deleteFavoriteMovie(favoriteMovieEntity)
    }

    suspend fun insertTrendingTv(trendingTvEntity: TrendingTvEntity) {
        tvDao.insertTrending(trendingTvEntity)
    }

    suspend fun insertPopularTv(popularTvEntity: PopularTvEntity) {
        tvDao.insertPopular(popularTvEntity)
    }

    suspend fun insertTopRatedTv(topRatedTvEntity: TopRatedTvEntity) {
        tvDao.insertTopRatedTv(topRatedTvEntity)
    }

    suspend fun insertFavoriteTv(favoriteEntity: FavoriteTvEntity) {
        tvDao.insertFavoriteTv(favoriteEntity)
    }

    suspend fun deleteFavoriteTv(favoriteTvEntity: FavoriteTvEntity) {
        tvDao.deleteFavoriteTv(favoriteTvEntity)
    }
}