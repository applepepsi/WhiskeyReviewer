package com.whiskeyReviewer.whiskeyreviewer.data

data class MyReviewFilterData(

    val day: MyReviewFilterItems=MyReviewFilterItems.New,
    val bottle: MyReviewFilterItems=MyReviewFilterItems.New,
)
