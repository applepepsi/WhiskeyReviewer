package com.example.whiskeyreviewer.view.toolBar

import com.example.whiskeyreviewer.R

sealed class ToolBarItems(
    val title: String,
    val icon: Int,
    val action: () -> Unit
) {
    data object TextSize : ToolBarItems("글씨 크기", R.drawable.text_size_icon, action = {

    })

    data object TextColor : ToolBarItems("글씨 색상", R.drawable.select_color_icon, action = {

    })

    data object Picture : ToolBarItems("사진 추가", R.drawable.select_picture_icon, action = {

    })

    companion object {
        val items = listOf(TextSize, TextColor, Picture)
    }
}


