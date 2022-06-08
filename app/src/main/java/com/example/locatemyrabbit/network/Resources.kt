package com.example.locatemyrabbit.network

import okhttp3.ResponseBody

sealed class Resources<out T>(
    val data: T? = null,
    val message: String? = null
) {
    data class Success<out T>(val value: T) : Resources<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resources<Nothing>()

    object Loading : Resources<Nothing>()
}
