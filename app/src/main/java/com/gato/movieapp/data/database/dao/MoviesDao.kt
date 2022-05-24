package com.gato.movieapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.gato.movieapp.data.database.entities.movie.*
import com.gato.movieapp.util.Constants.Companion.FAVORITES_MOVIE_TABLE
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_TABLE
import com.gato.movieapp.util.Constants.Companion.UP_COMING_MOVIE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTrending(trendingMovieEntity: TrendingMovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTopRatedMovies(topRatedMovieEntity: TopRatedMovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertPopular(popularMovieEntity: PopularMovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertUpComing(upComingMovieEntity: UpComingMovieEntity)

    @Query("SELECT * FROM $FAVORITES_MOVIE_TABLE")
    fun readFavoriteMovie(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM $TRENDING_MOVIE_TABLE")
    fun readTrending(): Flow<List<TrendingMovieEntity>>

    @Query("SELECT * FROM $TOP_RATED_MOVIE_TABLE")
    fun readTopRated(): Flow<List<TopRatedMovieEntity>>

    @Query("SELECT * FROM $POPULAR_MOVIE_TABLE")
    fun readPopular(): Flow<List<PopularMovieEntity>>

    @Query("SELECT * FROM $UP_COMING_MOVIE_TABLE")
    fun readUpComing(): Flow<List<UpComingMovieEntity>>

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTrendingSeeAll(trendingSeeAllMovieEntity: List<TrendingSeeAllMovieEntity>)

    @Query("SELECT * FROM $TRENDING_MOVIE_SA_TABLE")
    fun readTrendingSeeAll(): PagingSource<Int, TrendingSeeAllMovieEntity>

    @Query("DELETE FROM $TRENDING_MOVIE_SA_TABLE")
    suspend fun clearAllTrendingSeeAll()

    @Insert(onConflict = REPLACE)
    suspend fun insertPopularSeeAll(popularSeeAllMovieEntity: List<PopularSeeAllMovieEntity>)

    @Query("SELECT * FROM $POPULAR_MOVIE_SA_TABLE")
    fun readPopularSeeAll(): PagingSource<Int, PopularSeeAllMovieEntity>

    @Query("DELETE FROM $POPULAR_MOVIE_SA_TABLE")
    suspend fun clearAllPopularSeeAll()

    @Insert(onConflict = REPLACE)
    suspend fun insertTopRatedSeeAll(topRatedSeeAllMovieEntity: List<TopRatedSeeAllMovieEntity>)

    @Query("SELECT * FROM $TOP_RATED_MOVIE_SA_TABLE")
    fun readTopRatedSeeAll(): PagingSource<Int, TopRatedSeeAllMovieEntity>

    @Query("DELETE FROM $TOP_RATED_MOVIE_SA_TABLE")
    suspend fun clearAllTRSeeAll()


}