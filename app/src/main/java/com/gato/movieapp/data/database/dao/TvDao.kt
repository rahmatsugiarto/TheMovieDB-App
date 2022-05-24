package com.gato.movieapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.gato.movieapp.data.database.entities.tv.*
import com.gato.movieapp.util.Constants.Companion.FAVORITES_TV_TABLE
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_TV_SA_TABLE
import com.gato.movieapp.util.Constants.Companion.TRENDING_TV_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteTv(favoriteTvEntity: FavoriteTvEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTrending(trendingTvEntity: TrendingTvEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTopRatedTv(topRatedTvEntity: TopRatedTvEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertPopular(popularTvEntity: PopularTvEntity)

    @Query("SELECT * FROM $FAVORITES_TV_TABLE")
    fun readFavoriteTv(): Flow<List<FavoriteTvEntity>>

    @Query("SELECT * FROM $TRENDING_TV_TABLE")
    fun readTrending(): Flow<List<TrendingTvEntity>>

    @Query("SELECT * FROM $TOP_RATED_TV_TABLE")
    fun readTopRated(): Flow<List<TopRatedTvEntity>>

    @Query("SELECT * FROM $POPULAR_TV_TABLE")
    fun readPopular(): Flow<List<PopularTvEntity>>

    @Delete
    suspend fun deleteFavoriteTv(favoriteTvEntity: FavoriteTvEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTrendingSeeAll(trendingSeeAllEntity: List<TrendingSeeAllTvEntity>)

    @Query("SELECT * FROM $TRENDING_TV_SA_TABLE")
    fun readTrendingSeeAll(): PagingSource<Int, TrendingSeeAllTvEntity>

    @Query("DELETE FROM $TRENDING_TV_SA_TABLE")
    suspend fun clearAllTrendingSeeAll()

    @Insert(onConflict = REPLACE)
    suspend fun insertPopularSeeAll(popularSeeAllEntity: List<PopularSeeAllTvEntity>)

    @Query("SELECT * FROM $POPULAR_TV_SA_TABLE")
    fun readPopularSeeAll(): PagingSource<Int, PopularSeeAllTvEntity>

    @Query("DELETE FROM $POPULAR_TV_SA_TABLE")
    suspend fun clearAllPopularSeeAll()

    @Insert(onConflict = REPLACE)
    suspend fun insertTopRatedSeeAll(topRatedSeeAllEntity: List<TopRatedSeeAllTvEntity>)

    @Query("SELECT * FROM $TOP_RATED_TV_SA_TABLE")
    fun readTopRatedSeeAll(): PagingSource<Int, TopRatedSeeAllTvEntity>

    @Query("DELETE FROM $TOP_RATED_TV_SA_TABLE")
    suspend fun clearAllTRSeeAll()

}