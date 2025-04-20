package com.example.whiskeyreviewer.data.pagingResponse

import com.example.whiskeyreviewer.data.ImageData

data class ImageState(
    val isOpened:Boolean,
    val extendedState:Boolean,
    val imageList:List<ImageData>
)
