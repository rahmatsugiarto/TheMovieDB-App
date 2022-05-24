package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.util.Constants.Companion.FAVORITES_MOVIE_TABLE


@Entity(tableName = FAVORITES_MOVIE_TABLE)
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieResult: MovieResult
)