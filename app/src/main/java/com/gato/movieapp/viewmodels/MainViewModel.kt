package com.gato.movieapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.gato.movieapp.data.Repository
import com.gato.movieapp.data.database.entities.movie.*
import com.gato.movieapp.data.database.entities.tv.FavoriteTvEntity
import com.gato.movieapp.data.database.entities.tv.PopularTvEntity
import com.gato.movieapp.data.database.entities.tv.TopRatedTvEntity
import com.gato.movieapp.data.database.entities.tv.TrendingTvEntity
import com.gato.movieapp.models.MoviesResponse
import com.gato.movieapp.models.TvResponse
import com.gato.movieapp.models.UpComingResponse
import com.gato.movieapp.util.Function.Companion.handleResponse
import com.gato.movieapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE*/
    val readTrendingMovies: LiveData<List<TrendingMovieEntity>> =
        repository.local.readTrendingMovies().asLiveData()
    val readPopularMovies: LiveData<List<PopularMovieEntity>> =
        repository.local.readPopularMovies().asLiveData()
    val readTopRatedMovies: LiveData<List<TopRatedMovieEntity>> =
        repository.local.readTopRatedMovies().asLiveData()
    val readFavoriteMovies: LiveData<List<FavoriteMovieEntity>> =
        repository.local.readFavoriteMovies().asLiveData()
    val readUpComingMovies: LiveData<List<UpComingMovieEntity>> =
        repository.local.readUpComingMovies().asLiveData()

    val readTrendingTv: LiveData<List<TrendingTvEntity>> =
        repository.local.readTrendingTv().asLiveData()
    val readPopularTv: LiveData<List<PopularTvEntity>> =
        repository.local.readPopularTv().asLiveData()
    val readTopRatedTv: LiveData<List<TopRatedTvEntity>> =
        repository.local.readTopRatedTv().asLiveData()
    val readFavoriteTv: LiveData<List<FavoriteTvEntity>> =
        repository.local.readFavoriteTv().asLiveData()

    private fun insertTrendingMovies(trendingMovieEntity: TrendingMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTrendingMovies(trendingMovieEntity)
        }
    }

    private fun insertPopularMovies(popularMovieEntity: PopularMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertPopularMovies(popularMovieEntity)
        }
    }

    private fun insertTopRatedMovies(topRatedMovieEntity: TopRatedMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTopRatedMovies(topRatedMovieEntity)
        }
    }

    fun insertFavoriteMovies(favoriteMovieEntity: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteMovies(favoriteMovieEntity)
        }
    }

    private fun insertUpComingMovies(upComingMovieEntity: UpComingMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUpComingMovies(upComingMovieEntity)
        }
    }

    fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteMovie(favoriteMovieEntity)
        }
    }

    private fun insertTrendingTv(trendingTvEntity: TrendingTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTrendingTv(trendingTvEntity)
        }
    }

    private fun insertPopularTv(popularTvEntity: PopularTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertPopularTv(popularTvEntity)
        }
    }

    private fun insertTopRatedTv(topRatedTvEntity: TopRatedTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTopRatedTv(topRatedTvEntity)
        }
    }

    fun insertFavoriteTv(favoriteTvEntity: FavoriteTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteTv(favoriteTvEntity)
        }
    }

    fun deleteFavoriteTv(favoriteTvEntity: FavoriteTvEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteTv(favoriteTvEntity)
        }
    }

    /**RETROFIT*/

    var trendingMoviesResponse: MutableLiveData<NetworkResult<MoviesResponse>> = MutableLiveData()
    var popularMoviesResponse: MutableLiveData<NetworkResult<MoviesResponse>> = MutableLiveData()
    var topRatedMoviesResponse: MutableLiveData<NetworkResult<MoviesResponse>> = MutableLiveData()
    var upComingMoviesResponse: MutableLiveData<NetworkResult<UpComingResponse>> = MutableLiveData()


    var trendingTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()
    var popularTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()
    var topRatedTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    fun getTrendingMovies(apiKey: String) = viewModelScope.launch {
        getTrendingMoviesSafeCall(apiKey)
    }

    fun getPopularMovies(queries: Map<String, String>) = viewModelScope.launch {
        getPopularMoviesSafeCall(queries)
    }

    fun getTopRatedMovies(queries: Map<String, String>) = viewModelScope.launch {
        getTopRatedMoviesSafeCall(queries)
    }

    fun getUpComingMovies(queries: Map<String, String>) = viewModelScope.launch {
        getUpComingMoviesSafeCall(queries)
    }

    fun getTrendingTv(apiKey: String) = viewModelScope.launch {
        getTrendingTvSafeCall(apiKey)
    }

    fun getPopularTv(queries: Map<String, String>) = viewModelScope.launch {
        getPopularTvSafeCall(queries)
    }

    fun getTopRatedTv(queries: Map<String, String>) = viewModelScope.launch {
        getTopRatedTvSafeCall(queries)
    }


    private suspend fun getTrendingMoviesSafeCall(apiKey: String) {
        trendingMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val trendingResponse = repository.remote.getTrendingMovies(apiKey)
                trendingMoviesResponse.value =
                    handleResponse(trendingResponse)
                val trendingMoviesResponse = trendingMoviesResponse.value!!.data
                if (trendingMoviesResponse != null) {
                    offlineCacheTrendingMovies(trendingMoviesResponse)
                }
            } catch (e: Exception) {
                trendingMoviesResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getPopularMoviesSafeCall(queries: Map<String, String>) {
        popularMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val popularResponse = repository.remote.getPopularMovies(queries)
                popularMoviesResponse.value =
                    handleResponse(popularResponse)
                val popularMoviesResponse = popularMoviesResponse.value!!.data
                if (popularMoviesResponse != null) {
                    offlineCachePopularMovies(popularMoviesResponse)
                }
            } catch (e: Exception) {
                popularMoviesResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getTopRatedMoviesSafeCall(queries: Map<String, String>) {
        topRatedMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val topRatedResponse = repository.remote.getTopRatedMovies(queries)
                topRatedMoviesResponse.value =
                    handleResponse(topRatedResponse)
                val topRatedMoviesResponse = topRatedMoviesResponse.value!!.data
                if (topRatedMoviesResponse != null) {
                    offlineCacheTopMovies(topRatedMoviesResponse)
                }
            } catch (e: Exception) {
                topRatedMoviesResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getUpComingMoviesSafeCall(queries: Map<String, String>) {
        upComingMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val upComingResponse = repository.remote.getUpComingMovies(queries)
                upComingMoviesResponse.value =
                    handleResponse(upComingResponse)
                val upComingMoviesResponse = upComingMoviesResponse.value!!.data
                if (upComingMoviesResponse != null) {
                    offlineCacheUpComingMovies(upComingMoviesResponse)
                }
            } catch (e: Exception) {
                upComingMoviesResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getTrendingTvSafeCall(apiKey: String) {
        trendingTvResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val trendingResponse = repository.remote.getTrendingTv(apiKey)
                trendingTvResponse.value =
                    handleResponse(trendingResponse)
                val trendingTvResponse = trendingTvResponse.value!!.data
                if (trendingTvResponse != null) {
                    offlineCacheTrendingTv(trendingTvResponse)
                }
            } catch (e: Exception) {
                trendingTvResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getPopularTvSafeCall(queries: Map<String, String>) {
        popularTvResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val popularResponse = repository.remote.getPopularTv(queries)
                popularTvResponse.value =
                    handleResponse(popularResponse)
                val popularTvResponse = popularTvResponse.value!!.data
                if (popularTvResponse != null) {
                    offlineCachePopularTv(popularTvResponse)
                }
            } catch (e: Exception) {
                popularTvResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    private suspend fun getTopRatedTvSafeCall(queries: Map<String, String>) {
        topRatedTvResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val topRatedResponse = repository.remote.getTopRatedTv(queries)
                topRatedTvResponse.value =
                    handleResponse(topRatedResponse)
                val topRatedTvResponse = topRatedTvResponse.value!!.data
                if (topRatedTvResponse != null) {
                    offlineCacheTopTv(topRatedTvResponse)
                }
            } catch (e: Exception) {
                topRatedTvResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }


    private fun offlineCacheTrendingMovies(trendingMovies: MoviesResponse) {
        val trendingMovieEntity = TrendingMovieEntity(trendingMovies)
        insertTrendingMovies(trendingMovieEntity)
    }

    private fun offlineCachePopularMovies(popularMovies: MoviesResponse) {
        val popularMovieEntity = PopularMovieEntity(popularMovies)
        insertPopularMovies(popularMovieEntity)
    }

    private fun offlineCacheTopMovies(topRatedMovies: MoviesResponse) {
        val topRatedMovieEntity = TopRatedMovieEntity(topRatedMovies)
        insertTopRatedMovies(topRatedMovieEntity)
    }

    private fun offlineCacheUpComingMovies(upComingMovies: UpComingResponse) {
        val upComingMovieEntity = UpComingMovieEntity(upComingMovies)
        insertUpComingMovies(upComingMovieEntity)
    }

    private fun offlineCacheTrendingTv(trendingTv: TvResponse) {
        val trendingMovieEntity = TrendingTvEntity(trendingTv)
        insertTrendingTv(trendingMovieEntity)
    }

    private fun offlineCachePopularTv(popularTv: TvResponse) {
        val popularMovieEntity = PopularTvEntity(popularTv)
        insertPopularTv(popularMovieEntity)
    }

    private fun offlineCacheTopTv(topRatedTv: TvResponse) {
        val topRatedMovieEntity = TopRatedTvEntity(topRatedTv)
        insertTopRatedTv(topRatedMovieEntity)
    }


//    private fun <T> handleResponse(
//        response: Response<T>
//    ): NetworkResult<T> {
//        Log.d("handleMoviesResponse", "handleMoviesResponse: ${response.message()}")
//        return when {
//            response.message().toString().contains("timeout") -> {
//                NetworkResult.Error("Timeout")
//            }
//            response.code() == 402 -> {
//                NetworkResult.Error("Invalid API Key")
//            }
//            response.isSuccessful -> {
//                NetworkResult.Success(response.body()!!)
//            }
//            else -> {
//                NetworkResult.Error(response.message())
//            }
//        }
//
//    }


    fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}