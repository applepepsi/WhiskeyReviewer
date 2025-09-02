package com.whiskeyReviewer.whiskeyreviewer.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.whiskeyReviewer.whiskeyreviewer.component.toolBar.TextColors
import com.whiskeyReviewer.whiskeyreviewer.component.toolBar.textColorList

object ColorCompare {
    fun colorsCompare(spanColor: Color, currentColor: Color): TextColors? {

        return if (spanColor.toArgb() != currentColor.toArgb()) {
            textColorList.find { it.color.toArgb() == spanColor.toArgb() }

        } else null
    }

}