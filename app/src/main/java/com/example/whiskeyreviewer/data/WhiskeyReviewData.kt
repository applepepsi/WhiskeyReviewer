package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WhiskeyReviewData(

    val content:String="",
    val is_anonymous:Boolean=false,
    val open_date: LocalDate= LocalDate.now(),
    val score: Double=1.0,
    val tags:List<String> = listOf(),
    val imageList:List<String> = emptyList()
)


data class WhiskyReviewData2(
    val uuid:String="",


    val image:String="",
    val score: Double=0.0,
    val whiskyType:String="",
    val strength:Double=0.0,
//    val year:Int=0, //출시년도

)