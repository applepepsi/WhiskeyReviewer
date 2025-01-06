package com.example.whiskeyreviewer.utils

import android.util.Log
import retrofit2.Response
import java.io.IOException

object ApiHandler {
    suspend fun <T> makeApiCall(tag:String="",apiCall: suspend () -> Response<T>): T? {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                Log.d("$tag API 호출 성공", response.body().toString())
                response.body()
            } else {
                Log.d("$tag API 호출 실패", "$tag 실패")
                null
            }
        } catch (e: IOException) {
            Log.e("$tag 연결 실패", "네트워크 에러: ${e.message}", e)
            null
        } catch (e: Exception) {
            Log.e("$tag 오류 발생", "에러: ${e.message}", e)
            null
        }
    }
}