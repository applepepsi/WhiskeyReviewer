package com.example.whiskeyreviewer.data

import com.example.whiskeyreviewer.R

sealed class FloatingActionButtonItems(
    val title: String,
    val icon: Int,
    val screenRoute: String
) {

    data object OldBottle : FloatingActionButtonItems("기존 병에 일기 작성", R.drawable.half_bottle, NEW_BOTTLE)
    data object NewBottle : FloatingActionButtonItems("새로운 병 추가", R.drawable.full_bottle, NEW_BOTTLE2)

    data object NewReview : FloatingActionButtonItems("새로운 리뷰 추가", R.drawable.half_bottle, NEW_REVIEW)

    data object NewWhiskey : FloatingActionButtonItems("새로운 위스키 추가", R.drawable.new_whisky, NEW_WHISKEY)

    data object CustomWhiskey : FloatingActionButtonItems("직접 위스키 추가", R.drawable.custom_whisky, CUSTOM_WHISKEY)

    companion object {
        const val NEW_BOTTLE = "OLD_BOTTLE"
        const val NEW_BOTTLE2 = "NEW_BOTTLE"
        const val NEW_WHISKEY = "NEW_WHISKY"
        const val CUSTOM_WHISKEY = "CUSTOM_WHISKY"
        const val NEW_REVIEW = "NEW_REVIEW"
    }
}