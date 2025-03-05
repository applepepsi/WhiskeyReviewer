package com.example.whiskeyreviewer.data

import java.time.LocalDateTime

data class SingleWhiskeyData(
    val whisky_uuid:String="",
    val korea_name:String?="",
    val english_name:String="",
    val score:Double=0.0,
    val bottled_year:Int?=null,
    val image_name: String?=null,
    val strength:Double=0.0,
    val category: String?="",
    val country:String="",
    val cask_type:String="",
    val open_date:String?=null,
    val memo:String="",
    val reg_date:String?=null,
//    val reg_date:LocalDateTime= LocalDateTime.MIN,
    val mod_date:String?=null,
    val image:ByteArray?=null,

)
