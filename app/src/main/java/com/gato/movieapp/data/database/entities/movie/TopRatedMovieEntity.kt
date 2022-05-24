package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.MoviesResponse
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_TABLE

@Entity(tableName = TOP_RATED_MOVIE_TABLE)
class TopRatedMovieEntity(var moviesResponse: MoviesResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}