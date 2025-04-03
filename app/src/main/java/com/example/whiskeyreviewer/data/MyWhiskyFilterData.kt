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
    val vote_order:WhiskeyFilterItems?=null,
    val score_order:WhiskeyFilterItems?=null,
    val date_order:WhiskeyFilterItems?=null,

)
