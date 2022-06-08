package com.example.locatemyrabbit.responses

data class UserRegistration(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val verificationCode: String,
    val username: String?,
    val password: String,
    val agreement: Boolean
)