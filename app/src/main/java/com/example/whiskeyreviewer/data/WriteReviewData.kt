package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WriteReviewData(
//    val reviewText:String="",
    val content:String="",
    val is_anonymous:Boolean=false,
    val open_date: LocalDate= LocalDate.now(),
    val tags:List<String> = listOf(),
    val score: Double=0.0
)
