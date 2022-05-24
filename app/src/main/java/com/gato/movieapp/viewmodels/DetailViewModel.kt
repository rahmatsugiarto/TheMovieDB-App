package com.gato.movieapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gato.movieapp.data.Repository
import com.gato.movieapp.models.CreditsResponse
import com.gato.movieapp.models.DetailMovieResponse
import com.gato.movieapp.models.DetailTvResponse
import com.gato.movieapp.util.Function.Companion.applyQueries
import com.gato.movieapp.util.Function.Companion.handleResponse
import com.gato.movieapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var detailMovieResponse: MutableLiveData<NetworkResult<DetailMovieResponse>> = MutableLiveData()
    var creditsMovieResponse: MutableLiveData<NetworkResult<CreditsResponse>> = MutableLiveData()
    var detailTvResponse: MutableLiveData<NetworkResult<DetailTvResponse>> = MutableLiveData()
    var creditsTvResponse: MutableLiveData<NetworkResult<CreditsResponse>> = MutableLiveData()


    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            detailMovieResponse.value = NetworkResult.Loading()
            try {
                val detailMovie = repository.remote.getDetailMovie(id, applyQueries())
                detailMovieResponse.value = handleResponse(detailMovie)

            } catch (e: Exception) {
                detailMovieResponse.value = NetworkResult.Error(e.message.toString())
            }
        }

    }

    fun getCreditsMovie(id: Int) {
        viewModelScope.launch {
            creditsMovieResponse.value = NetworkResult.Loading()
            try {
                val creditsMovie = repository.remote.getCreditsMovie(id, applyQueries())
                creditsMovieResponse.value = handleResponse(creditsMovie)

            } catch (e: Exception) {
                creditsMovieResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }

    fun getDetailTv(id: Int) {
        viewModelScope.launch {
            detailTvResponse.value = NetworkResult.Loading()
            try {
                val detailTv = repository.remote.getDetailTv(id, applyQueries())
                detailTvResponse.value = handleResponse(detailTv)

            } catch (e: Exception) {
                detailTvResponse.value = NetworkResult.Error(e.message.toString())
            }
        }

    }

    fun getCreditsTv(id: Int) {
        viewModelScope.launch {
            creditsTvResponse.value = NetworkResult.Loading()
            try {
                val creditsTv = repository.remote.getCreditsTv(id, applyQueries())
                creditsTvResponse.value = handleResponse(creditsTv)

            } catch (e: Exception) {
                creditsTvResponse.value = NetworkResult.Error(e.message.toString())
            }
        }
    }


//    private fun applyQueries(): HashMap<String, String> {
//        val queries: HashMap<String, String> = HashMap()
//        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
//        queries[Constants.QUERY_LANGUAGE] = Constants.DEFAULT_QUERY_LANGUAGE
//
//        return queries
//    }

//    private fun <T> handleMoviesResponse(
//        response: Response<T>
//    ): NetworkResult<T> {
//        when {
//            response.message().toString().contains("timeout") -> {
//                return NetworkResult.Error("Timeout")
//            }
//            response.code() == 402 -> {
//                return NetworkResult.Error("Invalid API Key")
//            }
//            response.code() == 404 -> {
//                return NetworkResult.Error("Not Found")
//            }
//            response.isSuccessful -> {
//                return NetworkResult.Success(response.body()!!)
//            }
//            else -> {
//                return NetworkResult.Error(response.message())
//            }
//        }
//    }
}