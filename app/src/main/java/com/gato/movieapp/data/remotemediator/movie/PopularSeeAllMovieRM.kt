package com.gato.movieapp.data.remotemediator.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gato.movieapp.data.database.MoviesDatabase
import com.gato.movieapp.data.database.entities.movie.PopularMovieRemoteKeys
import com.gato.movieapp.data.database.entities.movie.PopularSeeAllMovieEntity
import com.gato.movieapp.data.network.Api
import com.gato.movieapp.util.Constants.Companion.DEFAULT_STARTING_PAGE_INDEX
import com.gato.movieapp.util.Function.Companion.applyQueries
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class PopularSeeAllMovieRM(
    private val database: MoviesDatabase,
    private val service: Api
) : RemoteMediator<Int, PopularSeeAllMovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularSeeAllMovieEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getPopularMovie(applyQueries(), page)
            val repos = apiResponse.body()?.movieResults
            val endOfPaginationReached = repos?.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeysPopularMovie()
                    database.moviesDao().clearAllPopularSeeAll()
                }
                val prevKey = if (page == DEFAULT_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached!!) null else page + 1
                val keys = repos.map {
                    PopularMovieRemoteKeys(repoId = 0, prevKey = prevKey, nextKey = nextKey)
                }
                val entities = repos.map {
                    PopularSeeAllMovieEntity(
                        id = 0,
                        idMovie = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath,
                        backdropPath = it.backdropPath,
                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage,
                        popularity = it.popularity,
                    )
                }
                database.remoteKeysDao().insertAllPopularMovie(keys)
                database.moviesDao().insertPopularSeeAll(entities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularSeeAllMovieEntity>): PopularMovieRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao().remoteKeysRepoIdPopularMovie(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularSeeAllMovieEntity>): PopularMovieRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDao().remoteKeysRepoIdPopularMovie(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PopularSeeAllMovieEntity>
    ): PopularMovieRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao().remoteKeysRepoIdPopularMovie(repoId)
            }
        }
    }
}




