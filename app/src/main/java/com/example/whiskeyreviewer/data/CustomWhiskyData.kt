package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class CustomWhiskyData(
    val whisky_name:String="",
    val category:String=TapLayoutItems.AmericanWhiskey.name!!,
    val strength:String="",
    val bottled_year:Int=LocalDate.now().year.toInt()
)
