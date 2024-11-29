package com.example.whiskeyreviewer.data

import java.time.LocalDate

data class WriteReviewData(
    val reviewText:String="",
    val reviewStyle:String="",
    val private:Boolean=false,
    val openDate: LocalDate= LocalDate.now()
)
