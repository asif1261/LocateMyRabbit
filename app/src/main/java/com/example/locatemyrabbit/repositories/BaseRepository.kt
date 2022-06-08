package com.example.locatemyrabbit.repositories

import com.example.locatemyrabbit.api.BaseApi
import com.example.locatemyrabbit.network.SafeApiCall

abstract class BaseRepository(
    private val api: BaseApi
) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}