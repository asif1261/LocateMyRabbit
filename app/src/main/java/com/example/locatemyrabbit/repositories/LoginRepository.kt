package com.ritesolution.ritebooks.login.repository

import com.example.locatemyrabbit.api.UserAPI
import com.example.locatemyrabbit.network.TokenManager
import com.example.locatemyrabbit.repositories.BaseRepository
import com.example.locatemyrabbit.responses.UserLogin
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: UserAPI,
    private val preferences: TokenManager,
): BaseRepository(api) {

    suspend fun signin(
        userRequest: UserLogin
    ) = safeApiCall {
        api.signin(userRequest)
    }

    suspend fun saveAccessToken(accessToken: String){
        preferences.saveToken(accessToken)
    }
}