package com.gato.movieapp.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gato.movieapp.data.DataStoreRepository
import com.gato.movieapp.data.SearchPagingMovie
import com.gato.movieapp.data.SearchPagingTv
import com.gato.movieapp.data.SearchType
import com.gato.movieapp.data.network.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: Api,
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) :
    AndroidViewModel(application) {

    val readSearchType = dataStoreRepository.readSearchType

    fun saveSearchType(searchType: String, searchTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSearchType(
                searchType,
                searchTypeId
            )
        }
    }

//    fun saveSearchTypeTemp(searchType: String, searchTypeId: Int) {
//        searchTypeDC = SearchType(searchType, searchTypeId)
//    }

    var loadingResponse: MutableLiveData<Boolean> = MutableLiveData()

//    @SuppressLint("CheckResult")
//    fun flowSearch(query: String): Pager<Int, Result> {
//        successResponse.value = true
//        try {
//            Pager(
//                // Configure how data is loaded by passing additional properties to
//                // PagingConfig, such as prefetchDistance.
//                PagingConfig(pageSize = 20)
//            ) {
//                SearchPagingSource(api, query)
//            }.flow
//                .cachedIn(viewModelScope)
//            successResponse.value = false
//        }catch (e: Exception){
//            errorResponse.value = e.message
//        }
//
//    }

    @SuppressLint("CheckResult")
    fun pagerSearchMovie(query: String) = Pager(
        PagingConfig(pageSize = 20)
    ) {
        SearchPagingMovie(api, query)
    }.flow
        .cachedIn(viewModelScope)

    @SuppressLint("CheckResult")
    fun pagerSearchTv(query: String) = Pager(
        PagingConfig(pageSize = 20)
    ) {
        SearchPagingTv(api, query)
    }.flow
        .cachedIn(viewModelScope)
}




