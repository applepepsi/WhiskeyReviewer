package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class CustomWhiskyData(
    val whisky_uuid:String="default",
    val korea_name:String="",
    val english_name:String="",
    val category:String=TapLayoutItems.AmericanWhiskey.name!!,
    val strength:String="",
    val country:String="",
    val bottled_year:Int=LocalDate.now().year.toInt(),
    val open_date:LocalDate= LocalDate.now(),
    val cask_type:String="",
    val tags:String=""
)
