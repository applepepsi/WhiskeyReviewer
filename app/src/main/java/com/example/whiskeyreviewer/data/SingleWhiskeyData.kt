package com.example.whiskeyreviewer.data

import android.net.Uri
import java.time.LocalDateTime

data class SingleWhiskeyData(
    val name:String="Test",
    val category: String?="",
    val strength:Double=0.0,
    val score:Double=0.0,
    val reg_date:String="",
    val release_year: Int? = null,
    val photo_url: String?=null,
    val whisky_uuid:String="",
//    val reg_date:LocalDateTime= LocalDateTime.MIN,
    val mod_date:LocalDateTime?=null
)
