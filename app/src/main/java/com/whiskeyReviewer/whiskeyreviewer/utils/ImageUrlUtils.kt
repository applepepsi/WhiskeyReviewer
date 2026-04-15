package com.whiskeyReviewer.whiskeyreviewer.utils

import android.net.Uri
import com.whiskeyReviewer.whiskeyreviewer.BuildConfig

object ImageUrlUtils {
    fun fromImageName(imageName: String): String {
        val baseUrl = BuildConfig.SERVER_ADDRESS.trimEnd('/')
        return "$baseUrl/image/${Uri.encode(imageName)}"
    }
}

