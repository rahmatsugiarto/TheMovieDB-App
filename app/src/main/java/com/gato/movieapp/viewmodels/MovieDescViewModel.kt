package com.gato.movieapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.gato.movieapp.data.database.MoviesDatabase
import com.gato.movieapp.data.network.Api
import com.gato.movieapp.data.remotemediator.movie.PopularSeeAllMovieRM
import com.gato.movieapp.data.remotemediator.movie.TopRatedSeeAllMovieRM
import com.gato.movieapp.data.remotemediator.movie.TrendingSeeAllMovieRM
import com.gato.movieapp.data.remotemediator.tv.PopularSeeAllTvRM
import com.gato.movieapp.data.remotemediator.tv.TopRatedSeeAllTvRM
import com.gato.movieapp.data.remotemediator.tv.TrendingSeeAllTvRM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDescViewModel @Inject constructor(
    api: Api,
    database: MoviesDatabase,
    application: Application
) :
    AndroidViewModel(application) {

    @OptIn(ExperimentalPagingApi::class)
    val pagerTrendingMovie = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = TrendingSeeAllMovieRM(database, api),
        pagingSourceFactory = { database.moviesDao().readTrendingSeeAll() }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val pagerPopularMovie = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = PopularSeeAllMovieRM(database, api),
        pagingSourceFactory = { database.moviesDao().readPopularSeeAll() }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val pagerTopRatedMovie = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = TopRatedSeeAllMovieRM(database, api),
        pagingSourceFactory = { database.moviesDao().readTopRatedSeeAll() }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val pagerTrendingTv = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = TrendingSeeAllTvRM(database, api),
        pagingSourceFactory = { database.tvDao().readTrendingSeeAll() }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val pagerPopularTv = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = PopularSeeAllTvRM(database, api),
        pagingSourceFactory = { database.tvDao().readPopularSeeAll() }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val pagerTopRatedTv = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = TopRatedSeeAllTvRM(database, api),
        pagingSourceFactory = { database.tvDao().readTopRatedSeeAll() }
    ).flow


}


