package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_SA_TABLE


@Entity(tableName = POPULAR_TV_SA_TABLE)
data class PopularSeeAllTvEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idTv: Int,
    val backdropPath: String?,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Double
)