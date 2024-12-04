package com.example.whiskeyreviewer.component.toolBar

import androidx.compose.ui.text.style.TextAlign

enum class TextStyleItems {
    TEXT_SIZE,
    TEXT_COLOR,
    TEXT_BACKGROUND_COLOR,

}

enum class TextAlignment {
    START, MID, END
}


data class TextStyleState(
    var bold: Boolean = false,
    var italic: Boolean = false,
    var underline: Boolean = false,
    var textSize: Boolean = false,
    var textBackgroundColor: Boolean = false,
    var textColor: Boolean = false,
    var textStartAlign: Boolean = false,
    var textMidAlign: Boolean = false,
    var textEndAlign: Boolean = false,
)
