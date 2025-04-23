package com.example.whiskeyreviewer.data

data class WhiskyName(
    val korea_name:String?="",
    val english_name:String="",
    val strength:String?=null,
    val country:String="",
    val whisky_uuid:String="",
    val check:Boolean?=null,
    val category: String?= null,
//    val category: TapLayoutItems=TapLayoutItems.AmericanWhiskey,
)
