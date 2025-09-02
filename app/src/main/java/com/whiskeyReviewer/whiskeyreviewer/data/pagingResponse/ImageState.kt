package com.whiskeyReviewer.whiskeyreviewer.data.pagingResponse

import com.whiskeyReviewer.whiskeyreviewer.data.ImageData

data class ImageState(
    val isOpened:Boolean,
    val extendedState:Boolean,
    val imageList:List<ImageData>
)
