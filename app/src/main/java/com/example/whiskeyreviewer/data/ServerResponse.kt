package com.example.whiskeyreviewer.data

data class ServerResponse<T>(
    val code: Int,
    val description: String,
    val data: T?=null,
    val errorCode: String? = null,
    val errorDescription: String? = null,
    val accessToken:String?=null,
)

