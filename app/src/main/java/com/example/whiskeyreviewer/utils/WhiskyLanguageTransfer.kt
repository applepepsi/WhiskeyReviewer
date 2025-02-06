package com.example.whiskeyreviewer.utils

import com.example.whiskeyreviewer.data.TapLayoutItems

object WhiskyLanguageTransfer {

    fun getKoreanTitle(name: String): String? {

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
            TapLayoutItems.Wheat
        )

        return items.find { it.name == name }?.title
    }
}