package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class CustomWhiskyData(
    val whisky_uuid:String="fdbd3b64-5fec-11f0-b0f9-00163ec21a5a",
    val image_name:String?=null,
    val korea_name:String="",
    val english_name:String="",
    val category:String=TapLayoutItems.SingleMalt.name!!,
    val strength:String="",
    val country:String="",
    val bottled_year:Int=LocalDate.now().year.toInt(),
    val open_date:String= LocalDate.now().toString(),
    val cask_type:String="",
    val memo:String=""
)
