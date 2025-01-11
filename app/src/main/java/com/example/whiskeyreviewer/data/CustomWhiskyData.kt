package com.example.whiskeyreviewer.data

data class CustomWhiskyData(
    val whisky_name:String="",
    val whisky_type:String=TapLayoutItems.AmericanWhiskey.name!!,
    val strength:String="",
    val sale_year:String=""
)
