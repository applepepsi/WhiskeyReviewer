package com.whiskeyReviewer.whiskeyreviewer.component.customComponent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MainTitleComponent(
    value:String,

) {
    Text(
        modifier = Modifier,
        text = value,
        style = TextStyle.Default.copy(
            color = Color.Black,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    )
}