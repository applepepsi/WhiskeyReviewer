package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WhiskyReviewData(

    val reviewStyle:String="",
    val private:Boolean=false,
    val openDate: LocalDate= LocalDate.now(),
    val score: Double=0.0,
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