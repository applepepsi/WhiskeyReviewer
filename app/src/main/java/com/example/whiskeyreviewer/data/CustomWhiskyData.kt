package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class CustomWhiskyData(
    val whisky_name:String="",
    val category:String=TapLayoutItems.AmericanWhiskey.name!!,
    val strength:String="",
    val bottled_year:Int=LocalDate.now().year.toInt(),
    val country:String="",
    val open_date:LocalDate= LocalDate.now(),
    val cask_type:String="",
    val whisky_eng_name:String="",
    val tag_Text:String=""
)
