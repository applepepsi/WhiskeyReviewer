package com.example.whiskeyreviewer.data

data class SubmitWhiskyData(
    val content: String,
    val is_anonymous: Boolean,
    val open_date: String,
    val tags: List<String>,
    val score: Int,
    val bottle_number: Int,
    val whisky_uuid:String
)
