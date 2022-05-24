package com.gato.movieapp.data.database.entities.tv

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_REMOTE_KEY_TABLE

@Entity(tableName = TOP_RATED_TV_REMOTE_KEY_TABLE)
data class TopRatedTvRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
