package com.example.whiskeyreviewer.data

data class WhiskyName(
    val whisky_name:String?="",
    val korea_name:String?="",
    val english_name:String="",
    val strength:String?=null,
    val country:String="",
    val whisky_uuid:String="",
    val check:Boolean?=null
)
