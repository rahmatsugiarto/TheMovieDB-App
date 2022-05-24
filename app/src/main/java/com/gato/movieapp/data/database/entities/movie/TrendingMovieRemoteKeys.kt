package com.gato.movieapp.data.database.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_REMOTE_KEY_TABLE

@Entity(tableName = TRENDING_MOVIE_REMOTE_KEY_TABLE)
data class TrendingMovieRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
