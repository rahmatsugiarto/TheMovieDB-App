package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.MoviesResponse
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_TABLE


@Entity(tableName = TRENDING_MOVIE_TABLE)
class TrendingMovieEntity(var moviesResponse: MoviesResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}