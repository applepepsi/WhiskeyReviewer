package com.whiskeyReviewer.whiskeyreviewer.utils

import android.util.Log
import retrofit2.Response
import java.io.IOException

object ApiHandler {
    suspend fun <T> makeApiCall(tag: String = "", apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("$tag API 호출 성공", body.toString())
                    ApiResult.Success(body)
                } else {
                    Log.d("$tag API 호출 실패", "$tag 실패: empty body")
                    ApiResult.HttpError(response.code(), "Empty body", response.errorBody()?.string())
                }
            } else {
                Log.d("$tag API 호출 실패", "$tag 실패: $response")
                ApiResult.HttpError(response.code(), response.message(), response.errorBody()?.string())
            }
        } catch (e: IOException) {
            Log.e("$tag 연결 실패", "네트워크 에러: ${e.message}", e)
            ApiResult.NetworkError(e)
        } catch (e: Exception) {
            Log.e("$tag 오류 발생", "에러: ${e.message}", e)
            ApiResult.UnknownError(e)
        }
    }
}