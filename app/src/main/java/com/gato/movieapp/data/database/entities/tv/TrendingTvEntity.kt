package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.TvResponse
import com.gato.movieapp.util.Constants.Companion.TRENDING_TV_TABLE


@Entity(tableName = TRENDING_TV_TABLE)
class TrendingTvEntity(var tvResponse: TvResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}