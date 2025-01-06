package com.example.whiskeyreviewer.data

data class WhiskyName(
    val whisky_name:String,
    val whisky_uuid:String,
    val is_first:Boolean,
    val check:Boolean?=null
)
