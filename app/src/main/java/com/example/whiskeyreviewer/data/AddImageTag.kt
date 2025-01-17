package com.example.whiskeyreviewer.data

sealed class AddImageTag(
    val tag:String
) {

    data object AddWhisky : AddImageTag("ADD_WHISKY")
    data object ChangeWhiskyImage : AddImageTag("CHANGE_WHISKY_IMAGE")
    data object InsertReview : AddImageTag("INSERT_REVIEW")
}

