package com.example.whiskeyreviewer.data

import android.net.Uri

data class SingleWhiskeyData(
    val name:String="Test",
    val capacity:Int=0,
    val score:Double=0.0,
    val dday:Int=0,
    val picture: Uri=Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000022")
)
