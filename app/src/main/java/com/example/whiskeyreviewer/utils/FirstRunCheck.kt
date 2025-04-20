package com.example.whiskeyreviewer.utils

import android.content.Context
import android.content.SharedPreferences

object FirstRunCheck {

    fun firstRunCheck(context: Context):Boolean {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            "FirstRunCheck",
            Context.MODE_PRIVATE
        )
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        return if(isFirstRun){
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
            true
        }else{
            false
        }
    }
}