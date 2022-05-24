package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_SA_TABLE


@Entity(tableName = TOP_RATED_MOVIE_SA_TABLE)
data class TopRatedSeeAllMovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var idMovie: Int,
    val backdropPath: String?,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double
)