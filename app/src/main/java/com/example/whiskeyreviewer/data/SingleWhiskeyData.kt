package com.example.whiskeyreviewer.data

import android.net.Uri

data class SingleWhiskeyData(
    val whisky_name:String="Test",
    val category: String="",
    val strength:Double=0.0,
    val score:Double=0.0,
    val dday:Int=0,
    val saleDate: Int = 0,

    val picture: Uri=Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000022")
)
