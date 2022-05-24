package com.gato.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gato.movieapp.data.database.entities.movie.PopularMovieRemoteKeys
import com.gato.movieapp.data.database.entities.movie.TopRatedMovieRemoteKeys
import com.gato.movieapp.data.database.entities.movie.TrendingMovieRemoteKeys
import com.gato.movieapp.data.database.entities.tv.PopularTvRemoteKeys
import com.gato.movieapp.data.database.entities.tv.TopRatedTvRemoteKeys
import com.gato.movieapp.data.database.entities.tv.TrendingTvRemoteKeys
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_REMOTE_KEY_TABLE
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_REMOTE_KEY_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_REMOTE_KEY_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_REMOTE_KEY_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_REMOTE_KEY_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_TV_REMOTE_KEY_TABLE

@Dao
interface RemoteKeysDao {

    //Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrendingMovie(trendingMovieRemoteKey: List<TrendingMovieRemoteKeys>)

    @Query("SELECT * FROM $TRENDING_MOVIE_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdTrendingMovie(repoId: Int): TrendingMovieRemoteKeys?

    @Query("DELETE FROM $TRENDING_MOVIE_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysTrendingMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularMovie(popularMovieRemoteKey: List<PopularMovieRemoteKeys>)

    @Query("SELECT * FROM $POPULAR_MOVIE_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdPopularMovie(repoId: Int): PopularMovieRemoteKeys?

    @Query("DELETE FROM $POPULAR_MOVIE_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysPopularMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedMovie(topRatedMovieRemoteKey: List<TopRatedMovieRemoteKeys>)

    @Query("SELECT * FROM $TOP_RATED_MOVIE_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdTopRatedMovie(repoId: Int): TopRatedMovieRemoteKeys?

    @Query("DELETE FROM $TOP_RATED_MOVIE_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysTopRatedMovie()

    //Tv
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrendingTv(trendingTvRemoteKey: List<TrendingTvRemoteKeys>)

    @Query("SELECT * FROM $TRENDING_TV_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdTrendingTv(repoId: Int): TrendingTvRemoteKeys?

    @Query("DELETE FROM $TRENDING_TV_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysTrendingTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularTv(popularTvRemoteKey: List<PopularTvRemoteKeys>)

    @Query("SELECT * FROM $POPULAR_TV_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdPopularTv(repoId: Int): PopularTvRemoteKeys?

    @Query("DELETE FROM $POPULAR_TV_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysPopularTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedTv(topRatedTvRemoteKey: List<TopRatedTvRemoteKeys>)

    @Query("SELECT * FROM $TOP_RATED_TV_REMOTE_KEY_TABLE WHERE repoId = :repoId")
    suspend fun remoteKeysRepoIdTopRatedTv(repoId: Int): TopRatedTvRemoteKeys?

    @Query("DELETE FROM $TOP_RATED_TV_REMOTE_KEY_TABLE")
    suspend fun clearRemoteKeysTopRatedTv()
}