package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.TvResponse
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_TABLE

@Entity(tableName = TOP_RATED_TV_TABLE)
class TopRatedTvEntity(var tvResponse: TvResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}