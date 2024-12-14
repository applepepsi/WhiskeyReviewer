package com.example.whiskeyreviewer.utils

import android.content.Context

import com.example.whiskeyreviewer.data.TokenData

object TokenManager {
    private const val PREFS_NAME = "tokenInfo"
//    private const val FCM_TOKEN_NAME = "fcmTokenName"
    private const val FCM_TOKEN = "fcmToken"
    private const val ACCESS_TOKEN_KEY = "accessToken"
    private const val REFRESH_TOKEN_KEY = "refreshToken"


    fun saveToken(context: Context, tokenData: TokenData) {
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = tokenInfo.edit()
        editor.putString(ACCESS_TOKEN_KEY, tokenData.accessToken)
        editor.putString(REFRESH_TOKEN_KEY, tokenData.refreshToken)
        editor.apply()
    }

    fun saveFcmToken(context: Context, tokenData: String){
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = tokenInfo.edit()
        editor.putString(FCM_TOKEN, tokenData)

        editor.apply()
    }

    fun clearToken(context: Context) {
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = tokenInfo.edit()
        editor.putString(ACCESS_TOKEN_KEY,null)
        editor.putString(REFRESH_TOKEN_KEY,null)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return tokenInfo.getString(ACCESS_TOKEN_KEY,null)
    }

    fun getRefreshToken(context: Context): String? {
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return tokenInfo.getString(REFRESH_TOKEN_KEY,null)
    }

    fun getFcmToken(context: Context): String? {
        val tokenInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return tokenInfo.getString(FCM_TOKEN,null)
    }

}