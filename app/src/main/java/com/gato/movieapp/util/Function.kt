package com.gato.movieapp.util

import android.util.Log
import retrofit2.Response

class Function {
    companion object {
        fun applyQueries(): HashMap<String, String> {
            val queries: HashMap<String, String> = HashMap()
            queries[Constants.QUERY_API_KEY] = Constants.API_KEY
            queries[Constants.QUERY_LANGUAGE] = Constants.DEFAULT_QUERY_LANGUAGE

            return queries
        }

        fun <T> handleResponse(
            response: Response<T>
        ): NetworkResult<T> {
            Log.d("handleResponse", "handleResponse: ${response.message()}")
            return when {
                response.message().toString().contains("timeout") -> {
                    NetworkResult.Error("Timeout")
                }
                response.code() == 402 -> {
                    NetworkResult.Error("Invalid API Key")
                }
                response.isSuccessful -> {
                    NetworkResult.Success(response.body()!!)
                }
                else -> {
                    NetworkResult.Error(response.message())
                }
            }

        }
    }
}