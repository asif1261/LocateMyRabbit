package com.example.locatemyrabbit.api

import okhttp3.ResponseBody
import retrofit2.http.POST

interface BaseApi {
    @POST("/api/Login/SignOut")
    suspend fun logout():ResponseBody
}