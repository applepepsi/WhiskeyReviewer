package com.example.whiskeyreviewer.data

data class WhiskyFilterData(
    val name:String="",
    val category: TapLayoutItems=TapLayoutItems.AllWhiskey,
    val date_order:WhiskeyFilterItems=WhiskeyFilterItems.DayAscendingOrder,
    val name_order:WhiskeyFilterItems=WhiskeyFilterItems.NameAscendingOrder,
    val score_order:WhiskeyFilterItems=WhiskeyFilterItems.ScoreAscendingOrder
)
