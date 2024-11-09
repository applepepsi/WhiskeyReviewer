package com.example.whiskeyreviewer.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.whiskeyreviewer.view.toolBar.TextColors
import com.example.whiskeyreviewer.view.toolBar.textColorList

object ColorCompare {
    fun colorsCompare(spanColor: Color, currentColor: Color): TextColors? {

        return if (spanColor.toArgb() != currentColor.toArgb()) {
            textColorList.find { it.color.toArgb() == spanColor.toArgb() }

        } else null
    }

}