package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.models.TvResponse
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_TABLE

@Entity(tableName = POPULAR_TV_TABLE)
class PopularTvEntity(var tvResponse: TvResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}