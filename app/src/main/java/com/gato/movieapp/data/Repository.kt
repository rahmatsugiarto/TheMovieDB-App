package com.gato.movieapp.data

import com.gato.movieapp.data.database.LocalDataSource
import javax.inject.Inject


class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}