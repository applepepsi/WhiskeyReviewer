package com.example.whiskeyreviewer.data

import com.example.whiskeyreviewer.R


sealed class WhiskyOptionItems(
    val title: String,
) {
    data object DeleteWhisky : WhiskyOptionItems("위스키 제거")

}
