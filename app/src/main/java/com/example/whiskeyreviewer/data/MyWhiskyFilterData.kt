package com.example.whiskeyreviewer.data

data class MyWhiskyFilterData(
    //todo 서버측에서 수정 안한듯, 초깃값을 null로 보내기
    val name:String="",
    val category: TapLayoutItems=TapLayoutItems.AllWhiskey,
    val date_order:WhiskeyFilterItems?=WhiskeyFilterItems.DayAscendingOrder,
//    val name_order:WhiskeyFilterItems?=null,
    val score_order:WhiskeyFilterItems?=WhiskeyFilterItems.DayAscendingOrder,
    val open_date_order:WhiskeyFilterItems?=WhiskeyFilterItems.DayAscendingOrder
)

data class ReviewFilterData(
    val searchText:String="",
    val detailSearchText: String="",
    val vote_order:WhiskeyFilterItems?=null,
    val score_order:WhiskeyFilterItems?=null,
    val date_order:WhiskeyFilterItems?=null,

)
