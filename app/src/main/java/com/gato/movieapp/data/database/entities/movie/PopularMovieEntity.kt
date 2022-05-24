package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.MoviesResponse
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_TABLE

@Entity(tableName = POPULAR_MOVIE_TABLE)
class PopularMovieEntity(var moviesResponse: MoviesResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}