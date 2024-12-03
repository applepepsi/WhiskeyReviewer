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



    data object New : MyReviewFilterItems("최신순", NEW, DAY)
    data object Old : MyReviewFilterItems("오래된순", OLD, DAY)


    data object Review : MyReviewFilterItems("리뷰", REVIEW, REVIEW_TYPE)
    data object Graph : MyReviewFilterItems("그래프", GRAPH, REVIEW_TYPE)

    companion object {
        const val BOTTLE_NUM = "병 번호"
        const val DAY = "작성일"
        const val REVIEW_TYPE="리뷰 종류"

        const val NEW = "NEW"
        const val OLD = "OLD"

        const val REVIEW = "REVIEW"
        const val GRAPH = "GRAPH"
    }
}

data class MyReviewFilterDropDownMenuState(
    var bottleNum:Boolean=false,
    var day: Boolean = false,
    var reviewType: Boolean = false,
)



