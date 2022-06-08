package com.example.locatemyrabbit.api

import com.example.locatemyrabbit.responses.UserLogin
import com.example.locatemyrabbit.responses.UserRegistration
import com.example.locatemyrabbit.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI: BaseApi {

    @POST("/user/signup")
    suspend fun signup(
        @Body userRequest: UserRegistration
    ) : UserResponse

    @POST("/user/signin")
    suspend fun signin(
        @Body userRequest: UserLogin
    ) : UserResponse

}