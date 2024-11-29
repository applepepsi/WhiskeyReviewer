package com.example.whiskeyreviewer.data

import com.example.whiskeyreviewer.R

sealed class FloatingActionButtonItems(
    val title: String,
    val icon: Int,
    val screenRoute: String
) {

    data object NewBottle : FloatingActionButtonItems("새로운 병 추가", R.drawable.setting_icon, NEW_BOTTLE)
    data object NewBottle2 : FloatingActionButtonItems("새로운 병 추가2", R.drawable.backup_icon, NEW_BOTTLE2)

    companion object {
        const val NEW_BOTTLE = "NEW_BOTTLE"
        const val NEW_BOTTLE2 = "NEW_BOTTLE2"
    }
}