package com.example.whiskeyreviewer.data.pagingResponse

data class ImageState(
    val isOpened:Boolean,
    val extendedState:Boolean,
    val imageList:List<ByteArray>
)
