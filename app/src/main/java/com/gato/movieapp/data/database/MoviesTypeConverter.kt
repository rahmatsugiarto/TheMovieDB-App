package com.gato.movieapp.data.database

import androidx.room.TypeConverter
import com.gato.movieapp.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun movieToString(movie: MoviesResponse): String = gson.toJson(movie)

    @TypeConverter
    fun stringToMovie(data: String): MoviesResponse {
        val listType = object : TypeToken<MoviesResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultMovieToString(movieResult: MovieResult): String = gson.toJson(movieResult)

    @TypeConverter
    fun stringToResultMovie(data: String): MovieResult {
        val listType = object : TypeToken<MovieResult>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun tvToString(tv: TvResponse): String = gson.toJson(tv)

    @TypeConverter
    fun stringToTv(data: String): TvResponse {
        val listType = object : TypeToken<TvResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultTvToString(tvResult: TvResult): String = gson.toJson(tvResult)

    @TypeConverter
    fun stringToResultTv(data: String): TvResult {
        val listType = object : TypeToken<TvResult>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun upComingMovieToString(up: UpComingResponse): String = gson.toJson(up)

    @TypeConverter
    fun stringToUpComingMovie(data: String): UpComingResponse {
        val listType = object : TypeToken<UpComingResponse>() {}.type
        return gson.fromJson(data, listType)
    }

//    @TypeConverter
//    fun resultUCMovieToString(tvResult: TvResult): String = gson.toJson(tvResult)
//
//    @TypeConverter
//    fun stringToResultUCMovie(data: String): TvResult {
//        val listType = object : TypeToken<TvResult>() {}.type
//        return gson.fromJson(data, listType)
//    }


}