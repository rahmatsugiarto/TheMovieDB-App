package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.util.Constants.Companion.FAVORITES_TV_TABLE


@Entity(tableName = FAVORITES_TV_TABLE)
class FavoriteTvEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tvResult: TvResult
)