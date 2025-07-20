package com.example.whiskeyreviewer.utils

import android.util.Log
import com.example.whiskeyreviewer.data.TapLayoutItems

object WhiskyLanguageTransfer {
    val items = listOf(
        TapLayoutItems.AllWhiskey,
        TapLayoutItems.SingleMalt,
        TapLayoutItems.BlendedMalt,
        TapLayoutItems.Blended,
        TapLayoutItems.BlendedGrain,
        TapLayoutItems.Grain,
        TapLayoutItems.Bourbon,
        TapLayoutItems.Rye,
        TapLayoutItems.Corn,
        TapLayoutItems.American,
        TapLayoutItems.Wheat,
        TapLayoutItems.CanadianWhiskey,
        TapLayoutItems.SinglePotStill,
        TapLayoutItems.Tennessee,
        TapLayoutItems.Spirit,
        TapLayoutItems.Other,
    )
    fun getKoreanTitle(name: String): String? {


        return items.find { it.name == name }?.title ?: TapLayoutItems.SingleMalt.title
    }

    fun finedWhiskyCategory(whisky:String): TapLayoutItems {
        Log.d("위스키", items.find{it.name==whisky}.toString())
        return items.find{it.name==whisky} ?: TapLayoutItems.AllWhiskey
    }
}