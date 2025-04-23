package com.example.whiskeyreviewer.utils

import android.util.Log
import com.example.whiskeyreviewer.data.TapLayoutItems

object WhiskyLanguageTransfer {
    val items = listOf(
        TapLayoutItems.AllWhiskey,
        TapLayoutItems.AmericanWhiskey,
        TapLayoutItems.Blend,
        TapLayoutItems.BlendedGrain,
        TapLayoutItems.BlendedMalt,
        TapLayoutItems.Bourbon,
        TapLayoutItems.CanadianWhiskey,
        TapLayoutItems.Corn,
        TapLayoutItems.Rice,
        TapLayoutItems.Rye,
        TapLayoutItems.SingleGrain,
        TapLayoutItems.SingleMalt,
        TapLayoutItems.SinglePotStill,
        TapLayoutItems.Spirit,
        TapLayoutItems.Tennessee,
        TapLayoutItems.Wheat,
        TapLayoutItems.Other
    )
    fun getKoreanTitle(name: String): String? {


        return items.find { it.name == name }?.title ?: TapLayoutItems.AmericanWhiskey.title
    }

    fun finedWhiskyCategory(whisky:String): TapLayoutItems {
        Log.d("위스키", items.find{it.name==whisky}.toString())
        return items.find{it.name==whisky} ?: TapLayoutItems.AllWhiskey
    }
}