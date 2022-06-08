package com.example.locatemyrabbit.responses

data class UserResponse(
    val token: String,
    val refreshToken: String,
    val user: User
)