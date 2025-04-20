package com.example.whiskeyreviewer.data

import android.net.Uri
import java.time.LocalDate

data class WhiskyReviewData(
    val review_uuid:String="",
    val korea_name:String?="",
    val english_name:String?="",
//    val user_whisky_uuid:String="",
    val content:String="",
    val is_anonymous:Boolean=true,
    val open_date: String= LocalDate.now().toString(),
    val score: Double=1.0,
    val tags:List<String> = listOf(),
    val image_names:List<String>?=null,
    val last_update_date:String="",
    val imageList:List<ImageData>? = emptyList(),
    val like_state:Boolean=false,
    val like_count:Int=0,
    val isOpened:Boolean=false,
    val expendedState:Boolean=false,
)



data class WhiskyReviewData2(
    val uuid:String="",


    val image:String="",
    val score: Double=0.0,
    val whiskyType:String="",
    val strength:Double=0.0,
//    val year:Int=0, //출시년도

)