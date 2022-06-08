package com.ritesolution.ritebooks.login.repository

import com.example.locatemyrabbit.api.UserAPI
import com.example.locatemyrabbit.network.TokenManager
import com.example.locatemyrabbit.repositories.BaseRepository
import com.example.locatemyrabbit.responses.UserLogin
import com.example.locatemyrabbit.responses.UserRegistration
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val api: UserAPI,
    private val preferences: TokenManager,
): BaseRepository(api) {

    suspend fun signin(
        userRequest: UserRegistration
    ) = safeApiCall {
        api.signup(userRequest)
    }

    suspend fun saveAccessToken(accessToken: String){
        preferences.saveToken(accessToken)
    }
}