package com.example.zenfirelite.apis.datamodels

data class UserAuth(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)
