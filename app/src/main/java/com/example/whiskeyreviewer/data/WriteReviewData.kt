package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WriteReviewData(
//    val reviewText:String="",
    val whiskey_uuid:String="",
    val content:String="",
    val is_anonymous:Boolean=false,
    val open_date: LocalDate= LocalDate.now(),
    val tags:List<String> = listOf(),
    val score: Double=1.0,
    val bottle_num:Int=0,
    val imageList:List<String> = listOf(),
    val whiskyName:String=""
)
