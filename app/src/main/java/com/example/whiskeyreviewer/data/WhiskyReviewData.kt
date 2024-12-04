package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WhiskyReviewData(
    val reviewText:String="",
    val reviewStyle:String="",
    val private:Boolean=false,
    val openDate: LocalDate= LocalDate.now(),
    val score: Double=0.0,
    val imageList:List<String> = emptyList()
)
