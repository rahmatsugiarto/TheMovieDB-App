package com.gato.movieapp.data.network

import com.gato.movieapp.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("trending/movie/day")
    suspend fun getTrendingMovie(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @QueryMap queries: Map<String, String>,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @QueryMap queries: Map<String, String>,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int,
        @QueryMap queries: Map<String, String>
    ): Response<DetailMovieResponse>

    @GET("movie/{id}/credits")
    suspend fun getCreditsMovie(
        @Path("id") id: Int,
        @QueryMap queries: Map<String, String>
    ): Response<CreditsResponse>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @QueryMap queries: Map<String, String>,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovie(
        @Query("api_key") apikey: String,
        @QueryMap queries: Map<String, String>
    ): Response<UpComingResponse>

    @GET("trending/tv/day")
    suspend fun getTrendingTv(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): Response<TvResponse>

    @GET("tv/popular")
    suspend fun getPopularTv(
        @QueryMap queries: Map<String, String>,
        @Query("page") page: Int
    ): Response<TvResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @QueryMap queries: Map<String, String>,
        @Query("page") page: Int
    ): Response<TvResponse>

    @GET("tv/{id}")
    suspend fun getDetailTv(
        @Path("id") id: Int,
        @QueryMap queries: Map<String, String>
    ): Response<DetailTvResponse>

    @GET("tv/{id}/credits")
    suspend fun getCreditsTv(
        @Path("id") id: Int,
        @QueryMap queries: Map<String, String>
    ): Response<CreditsResponse>

    @GET("search/tv")
    suspend fun getSearchTv(
        @QueryMap queries: Map<String, String>,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<TvResponse>

}