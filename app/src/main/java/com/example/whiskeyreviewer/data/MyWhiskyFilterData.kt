package com.example.whiskeyreviewer.data

data class MyWhiskyFilterData(
    val name:String="",
    val category: TapLayoutItems=TapLayoutItems.AllWhiskey,
    val date_order:WhiskeyFilterItems?=WhiskeyFilterItems.DayAscendingOrder,
    val name_order:WhiskeyFilterItems?=WhiskeyFilterItems.NameAscendingOrder,
    val score_order:WhiskeyFilterItems?=WhiskeyFilterItems.ScoreAscendingOrder
)

data class ReviewFilterData(
    val searchText:String="",
    val detailSearchText: String="",
    val vote_order:WhiskeyFilterItems?=WhiskeyFilterItems.VoteAscendingOrder,
    val score_order:WhiskeyFilterItems?=WhiskeyFilterItems.ScoreAscendingOrder,
    val date_order:WhiskeyFilterItems?=WhiskeyFilterItems.DayAscendingOrder,

)
