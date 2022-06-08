package com.example.locatemyrabbit.responses

data class UserLogin(
    val username: String,
    val password: String,
    val agreement: Boolean
)