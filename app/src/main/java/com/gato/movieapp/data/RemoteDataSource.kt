package com.gato.movieapp.data

import com.gato.movieapp.data.network.Api
import com.gato.movieapp.models.*
import com.gato.movieapp.util.Constants.Companion.API_KEY
import com.gato.movieapp.util.Constants.Companion.DEFAULT_STARTING_PAGE_INDEX
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: Api) {

    suspend fun getTrendingMovies(apiKey: String): Response<MoviesResponse> {
        return api.getTrendingMovie(apiKey, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getPopularMovies(queries: Map<String, String>): Response<MoviesResponse> {
        return api.getPopularMovie(queries, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getTopRatedMovies(queries: Map<String, String>): Response<MoviesResponse> {
        return api.getTopRatedMovie(queries, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getDetailMovie(
        movieId: Int,
        queries: Map<String, String>
    ): Response<DetailMovieResponse> {
        return api.getDetailMovie(movieId, queries)
    }

    suspend fun getCreditsMovie(
        movieId: Int,
        queries: Map<String, String>
    ): Response<CreditsResponse> {
        return api.getCreditsMovie(movieId, queries)
    }

    suspend fun getUpComingMovies(queries: Map<String, String>): Response<UpComingResponse> {
        return api.getUpComingMovie(API_KEY, queries)
    }


    suspend fun getTrendingTv(apiKey: String): Response<TvResponse> {
        return api.getTrendingTv(apiKey, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getPopularTv(queries: Map<String, String>): Response<TvResponse> {
        return api.getPopularTv(queries, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getTopRatedTv(queries: Map<String, String>): Response<TvResponse> {
        return api.getTopRatedTv(queries, DEFAULT_STARTING_PAGE_INDEX)
    }

    suspend fun getDetailTv(
        movieId: Int,
        queries: Map<String, String>
    ): Response<DetailTvResponse> {
        return api.getDetailTv(movieId, queries)
    }

    suspend fun getCreditsTv(
        movieId: Int,
        queries: Map<String, String>
    ): Response<CreditsResponse> {
        return api.getCreditsTv(movieId, queries)
    }


}