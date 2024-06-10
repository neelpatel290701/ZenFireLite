package com.example.zenfirelite.apis.datamodels

data class UserAuthRequestBody(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)
