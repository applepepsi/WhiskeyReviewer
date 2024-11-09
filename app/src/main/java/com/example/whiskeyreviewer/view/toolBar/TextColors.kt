package com.example.whiskeyreviewer.view.toolBar

import androidx.compose.ui.graphics.Color

data class TextColors(
    val color: Color,
    val index: Int
)

val colorList = listOf(
    Color(0xFF000000), // Black
    Color(0xFFFFFFFF),  // White
    Color(0xFFFF0000), // Red
    Color(0xFFFFA500), // Orange
    Color(0xFFFFC0CB), // Pink
    Color(0xFF00FF00), // Green
    Color(0xFF008000), // Dark Green
    Color(0xFF20B2AA), // Light Sea Green
    Color(0xFF0000FF), // Blue
    Color(0xFFADD8E6), // Light Blue
    Color(0xFF000080), // Navy
    Color(0xFFFFFF00), // Yellow
    Color(0xFFFFD700), // Gold
    Color(0xFF808080), // Gray
    Color(0xFFD3D3D3), // Light Gray
    Color(0xFFA9A9A9), // Dark Gray
    Color(0xFF800080), // Purple
    Color(0xFFFF00FF), // Magenta
)
val textColorList = colorList.mapIndexed { index, color -> TextColors(color, index) }
