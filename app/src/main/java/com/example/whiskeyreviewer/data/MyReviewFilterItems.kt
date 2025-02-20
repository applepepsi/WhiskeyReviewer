package com.example.whiskeyreviewer.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.DAY_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.DAY_DESCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.OPEN_DATE_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.OPEN_DATE_DESCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.SCORE_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.SCORE_DESCENDING_ORDER

sealed class MyReviewFilterItems(
    val title: String,
    val orderType: String,
    val type:String,

) {



    data object New : MyReviewFilterItems("최신순", ASC, DAY)
    data object Old : MyReviewFilterItems("오래된순", DESC, DAY)

    data object Best : MyReviewFilterItems("추천 많은순", BEST, VOTE)
    data object Worst : MyReviewFilterItems("추천 적은순", WORST, VOTE)

    data object Review : MyReviewFilterItems("리뷰", REVIEW, REVIEW_TYPE)
    data object Graph : MyReviewFilterItems("그래프", GRAPH, REVIEW_TYPE)

    data object Detail : MyReviewFilterItems("세부 정보", DETAIL, REVIEW_TYPE)

    companion object {
        const val BOTTLE_NUM = "병 번호"
        const val DAY = "작성일"
        const val REVIEW_TYPE="리뷰 종류"
        const val VOTE = "추천순"

        //todo 최신순 오래된순인데 서버에서 착각한듯 말해야함
        const val ASC = "asc"
        const val DESC = "desc"

        const val REVIEW = "REVIEW"
        const val GRAPH = "GRAPH"
        const val DETAIL = "DETAIL"

        const val BEST = "BEST"
        const val WORST = "WORST"
    }
}

data class MyReviewFilterDropDownMenuState(
    var bottleNum:Boolean=false,
    var day: Boolean = false,
    var reviewType: Boolean = false,
    var vote: Boolean=false
)



