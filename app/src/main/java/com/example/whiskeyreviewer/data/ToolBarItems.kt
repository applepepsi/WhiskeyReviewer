package com.example.whiskeyreviewer.data

import com.example.whiskeyreviewer.R

sealed class ToolBarItems(
    val title: String,
    val icon: Int,

) {
    data object TextStyle : ToolBarItems("텍스트 스타일", R.drawable.text_size_icon)


    data object Picture : ToolBarItems("사진 추가", R.drawable.select_picture_icon)

    data object SelectDate : ToolBarItems("개봉일 선택", R.drawable.calender_icon)



    companion object {
        val items = listOf(TextStyle, Picture , SelectDate)
    }
}


