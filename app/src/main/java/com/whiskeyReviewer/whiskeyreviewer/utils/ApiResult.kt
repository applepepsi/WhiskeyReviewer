package com.whiskeyReviewer.whiskeyreviewer.utils

import java.io.IOException

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class HttpError(val code: Int, val message: String?, val errorBody: String?) : ApiResult<Nothing>()
    data class NetworkError(val exception: IOException) : ApiResult<Nothing>()
    data class UnknownError(val exception: Throwable) : ApiResult<Nothing>()
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.Success(transform(data))
        is ApiResult.HttpError -> this
        is ApiResult.NetworkError -> this
        is ApiResult.UnknownError -> this
    }
}

fun ApiResult<*>.asThrowable(): Throwable {
    return when (this) {
        is ApiResult.Success -> IllegalStateException("Success has no error")
        is ApiResult.HttpError -> RuntimeException("HTTP ${code}: ${message ?: "Unknown error"}")
        is ApiResult.NetworkError -> exception
        is ApiResult.UnknownError -> exception
    }
}

