package com.gato.movieapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gato.movieapp.data.network.Api
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.util.Constants.Companion.DEFAULT_STARTING_PAGE_INDEX
import com.gato.movieapp.util.Function.Companion.applyQueries
import okio.IOException
import retrofit2.HttpException

class SearchPagingTv(
    private val service: Api,
    private val query: String,
) : PagingSource<Int, TvResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvResult> {
        val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
        return try {
            val response =
                service.getSearchTv(applyQueries(), query, position)
            Log.d("RRSS", "load: ${response.body()?.tvResults}")
            val repos = response.body()?.tvResults ?: emptyList()
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, TvResult>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}