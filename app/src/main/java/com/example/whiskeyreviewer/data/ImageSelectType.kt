package com.example.whiskeyreviewer.data

data class ImageSelectState(

    val albumSelected: Boolean = true,
    val cameraSelected: Boolean = false
)

enum class ImageSelectType {
    ALBUM,
    CAMERA
}

