package com.example.whiskeyreviewer.data

data class WhiskyName(
    val whisky_name:String,
    val whisky_uuid:String,
    val is_first:Boolean,
    //병입년도도 추가해야할듯
    val check:Boolean?=null
)
