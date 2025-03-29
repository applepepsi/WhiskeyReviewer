package com.example.whiskeyreviewer.data

import android.net.Uri
import java.time.LocalDate

data class WhiskyReviewData(
    val review_uuid:String="",
    val user_whisky_uuid:String="",
    val content:String="",
    val is_anonymous:Boolean=false,
    val open_date: String= LocalDate.now().toString(),
    val score: Double=1.0,
    val tags:List<String> = listOf(),
    val image_names:List<String>?=null,
    val imageList:List<ByteArray>? = emptyList(),
    val like_state:Boolean=false,
    val like_count:Int=0,
)

sealed class ImageData {
    data class StringData(val name: String) : ImageData()
    data class ByteArrayData(val byteArray: ByteArray) : ImageData()

    data class UriData(val uri: Uri) :ImageData()
}


data class WhiskyReviewData2(
    val uuid:String="",


    val image:String="",
    val score: Double=0.0,
    val whiskyType:String="",
    val strength:Double=0.0,
//    val year:Int=0, //출시년도

)