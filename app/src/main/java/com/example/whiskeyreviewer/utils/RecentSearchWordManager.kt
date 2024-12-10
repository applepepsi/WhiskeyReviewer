package com.example.whiskeyreviewer.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object RecentSearchWordManager {

    fun loadRecentSearchList(context: Context,type:String): MutableList<String> {
        val sharedPreferences = context.getSharedPreferences(type, Context.MODE_PRIVATE)
        return getRecentSearchList(sharedPreferences,type)
    }

    fun saveSearchText(context: Context, searchText: String,type:String): MutableList<String> {
        val sharedPreferences = context.getSharedPreferences(type, Context.MODE_PRIVATE)
        val recentSearchList = getRecentSearchList(sharedPreferences,type)
        Log.d("searchText",searchText)
        if (!recentSearchList.contains(searchText)) {
            recentSearchList.add(0, searchText)
            if (recentSearchList.size > 20) {
                recentSearchList.removeAt(recentSearchList.size - 1)
            }
            saveRecentSearchList(sharedPreferences, recentSearchList,type)
        }

        return recentSearchList
    }

    fun deleteRecentSearchText(context: Context, searchText: String,type:String): MutableList<String> {
        val sharedPreferences = context.getSharedPreferences(type, Context.MODE_PRIVATE)
        val recentSearchList = getRecentSearchList(sharedPreferences,type)

        if (recentSearchList.remove(searchText)) {
            saveRecentSearchList(sharedPreferences, recentSearchList,type)
        }

        return recentSearchList
    }

    private fun getRecentSearchList(sharedPreferences: SharedPreferences,type:String): MutableList<String> {
        val savedString = sharedPreferences.getString(type, "")
        return savedString?.split(",")?.toMutableList() ?: mutableListOf()
    }

    private fun saveRecentSearchList(sharedPreferences: SharedPreferences, recentSearchList: List<String>,type:String) {
        val editor = sharedPreferences.edit()
        val newSavedString = recentSearchList.joinToString(",")
        editor.putString(type, newSavedString)
        editor.apply()
    }
}