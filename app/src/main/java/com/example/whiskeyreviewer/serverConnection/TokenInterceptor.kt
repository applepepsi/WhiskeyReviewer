package com.example.whiskeyreviewer.serverConnection

import android.content.Context
import android.content.Intent
import android.util.Log

import com.example.nextclass.utils.EXPIRED_ACCESS_TOKEN
import com.example.nextclass.utils.EXPIRED_REFRESH_TOKEN
import com.example.nextclass.utils.INVALID_ACCESS_TOKEN
import com.example.nextclass.utils.INVALID_REFRESH_TOKEN
import com.example.nextclass.utils.REGISTER

import com.example.nextclass.utils.TOKEN_USER_NOT_EXIST
import com.example.whiskeyreviewer.data.TokenData

import com.example.whiskeyreviewer.utils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class TokenInterceptor(
    private val context: Context,

    private val excludedPaths: List<String> = listOf(
        REGISTER
    )
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestPath = originalRequest.url.encodedPath

        if (excludedPaths.contains(requestPath)) {
            return chain.proceed(originalRequest)
        }


        // 토큰을 가져옴 todo
        val accessToken = TokenManager.getAccessToken(context)


        if (accessToken != null) {
            Log.d("newToken",accessToken)
        }
        val testAccessToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NjM3NmM4ZS1lNDkwLTQyOWUtOGQ5Yy0wYzczYjczMmE0ZDc6VVNFUiIsImlzcyI6IkRhZUhhbiIsImlhdCI6MTcyMzYxMDI0MSwiZXhwIjoxNzIzNjIxMDQxfQ.IKutGRUxsUmHK-5370gY7p7ImGjcIesC_Q7z64h9hNfMdwCjlr9PfPDtT1W4J-8yOyaylxoAB24ylhl5IvzGnA"

        // 새로운 요청 빌더 생성
        val newRequest = originalRequest.newBuilder().apply {
            accessToken?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()


        var response=chain.proceed(newRequest)

        if (isTokenExpired(response)) {
            Log.d("토큰만료","만료")
            // 토큰 갱신 로직을 실행


            //새로운 엑세스 키를 가져옴
            val newAccessToken = refreshAccessToken(chain, originalRequest)

            //새로운 엑세스 키를 받아왔다면
            if (newAccessToken != null) {
                Log.d("뉴엑세스토큰",newAccessToken)
                // 처음 시도했던 통신을 이어가기 위해 새로운 Access Token을 사용하여 요청을 다시 보냄
                val lastRequest = originalRequest.newBuilder().apply {
                    header("Authorization", "Bearer $newAccessToken")
                }.build()
                response = chain.proceed(lastRequest)
            }else {
                //새로운 엑세스 키를 받아오지 못했다면

            }
        }


        return response
    }
    private fun isTokenExpired(response: Response): Boolean {
        //서버의 응답에서 EXPIRED_ACCESS_TOKEN코드가 온다면 토큰 재발급 시작
        val responseBody = response.peekBody(Long.MAX_VALUE).string()
        Log.d("responseBody",responseBody)

        return responseBody.contains(EXPIRED_ACCESS_TOKEN) || responseBody.contains(INVALID_ACCESS_TOKEN) || responseBody.contains(
                TOKEN_USER_NOT_EXIST)

    }

    private fun isRefreshTokenExpired(response: Response): Boolean {

        val responseBody = response.peekBody(Long.MAX_VALUE).string()
        Log.d("RefreshTokenExpiredResponseBody",responseBody)
        return responseBody.contains(EXPIRED_REFRESH_TOKEN) || responseBody.contains(
            INVALID_REFRESH_TOKEN) || responseBody.contains(TOKEN_USER_NOT_EXIST)

//        return responseBody.contains(EXPIRED_REFRESH_TOKEN)
    }

    private fun refreshAccessToken(chain: Interceptor.Chain, originalRequest: Request): String? {
        return runBlocking {
            try {
                // 리프레시 토큰 가져옴
                val refreshToken = TokenManager.getRefreshToken(context)
                val accessToken = TokenManager.getAccessToken(context)

                val testAccessToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NjM3NmM4ZS1lNDkwLTQyOWUtOGQ5Yy0wYzczYjczMmE0ZDc6VVNFUiIsImlzcyI6IkRhZUhhbiIsImlhdCI6MTcyMzYxMDI0MSwiZXhwIjoxNzIzNjIxMDQxfQ.IKutGRUxsUmHK-5370gY7p7ImGjcIesC_Q7z64h9hNfMdwCjlr9PfPDtT1W4J-8yOyaylxoAB24ylhl5IvzGnA"
                val testRefreshToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzJiMDAyMS05ZDhjLTRhZmYtOGMwOC03NmMzNDc4ZjY2NzY6VVNFUiIsImlzcyI6IkRhZUhhbiIsImlhdCI6MTcyMzU0OTM0OCwiZXhwIjoxNzIzNzU2OTQ4fQ.Ruj60RcEr_xA16sRMXUcKhmC3yzi7j0ySAos9Q9n5_UNqF9S6N_PRXXTgPByMrroVVhhTdLmYJ6eNCMzeiWcWA"

                // 토큰 갱신 요청 생성
                val newAccessTokenRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .header("refresh-token", refreshToken!!)
                    .build()



                // 토큰 갱신 요청
                val refreshTokenResponse = chain.proceed(newAccessTokenRequest)

//                Log.d("엑세스 재발급 응답", refreshTokenResponse.toString())

                if(isRefreshTokenExpired(refreshTokenResponse)){
                    //만약 리프레쉬 토큰도 만료라면 로그인화면으로 이동
                    Log.d("리프레시 만료", EXPIRED_REFRESH_TOKEN)
                    return@runBlocking null
                }
                else{
                    if (refreshTokenResponse.isSuccessful) {
                        // 새로운 엑세스키 추출
                        val responseBody = refreshTokenResponse.peekBody(Long.MAX_VALUE).string()

                        val jsonObject = JSONObject(responseBody)
                        Log.e("jsonObject", jsonObject.toString())
                        val newAccessToken = jsonObject.getString("accessToken")

                        // 새로운 Access Token 저장
                        TokenManager.saveToken(context, TokenData(refreshToken!!, newAccessToken))

                        return@runBlocking newAccessToken

                    }
                }
            } catch (e: Exception) {
                Log.e("TokenInterceptor", "Failed to refresh token", e)
            }
            return@runBlocking null
        }
    }
}

