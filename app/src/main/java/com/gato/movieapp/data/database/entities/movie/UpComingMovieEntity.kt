package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.UpComingResponse
import com.gato.movieapp.util.Constants.Companion.UP_COMING_MOVIE_TABLE

@Entity(tableName = UP_COMING_MOVIE_TABLE)
class UpComingMovieEntity(var upComingResponse: UpComingResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}