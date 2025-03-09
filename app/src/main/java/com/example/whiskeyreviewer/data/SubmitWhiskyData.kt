package com.example.whiskeyreviewer.data

data class SubmitWhiskyData(
    val content: String,
    val is_anonymous: Boolean,
    val open_date: String,
    val tags: List<String>,
    val score: Int,
    val my_whisky_uuid:String,
    val image_names:List<String?>?=null,
)
