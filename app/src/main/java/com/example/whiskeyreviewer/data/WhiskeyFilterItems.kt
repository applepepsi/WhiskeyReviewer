package com.example.whiskeyreviewer.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.DAY_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.DAY_DESCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.OPEN_DATE_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.OPEN_DATE_DESCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.SCORE_ASCENDING_ORDER
import com.example.whiskeyreviewer.data.WhiskeyFilterItems.Companion.SCORE_DESCENDING_ORDER

sealed class WhiskeyFilterItems(
    val title: String,
    val orderType: String,
    val type:String,
    val icon: Int,
) {

    data object DayAscendingOrder : WhiskeyFilterItems("작성일 오름차순", DAY_ASCENDING_ORDER, DAY, R.drawable.arrow_up)
    data object DayDescendingOrder : WhiskeyFilterItems("작성일 내림차순", DAY_DESCENDING_ORDER, DAY, R.drawable.arrow_down)


    data object ScoreAscendingOrder : WhiskeyFilterItems("점수 오름차순", SCORE_ASCENDING_ORDER, SCORE, R.drawable.arrow_up)
    data object ScoreDescendingOrder : WhiskeyFilterItems("점수 내림차순", SCORE_DESCENDING_ORDER, SCORE, R.drawable.arrow_down)


    data object OpenDateAscendingOrder : WhiskeyFilterItems("개봉일 오름차순", OPEN_DATE_ASCENDING_ORDER, OPEN_DATE, R.drawable.arrow_up)
    data object OpenDateDescendingOrder : WhiskeyFilterItems("개봉일 내림차순", OPEN_DATE_DESCENDING_ORDER, OPEN_DATE, R.drawable.arrow_down)

    companion object {
        const val DAY = "작성일"
        const val SCORE="점수"
        const val OPEN_DATE="개봉일"

        const val DAY_ASCENDING_ORDER = "DAY_ASCENDING_ORDER"
        const val DAY_DESCENDING_ORDER = "DAY_DESCENDING_ORDER"

        const val SCORE_ASCENDING_ORDER = "SCORE_ASCENDING_ORDER"
        const val SCORE_DESCENDING_ORDER = "SCORE_DESCENDING_ORDER"

        const val OPEN_DATE_ASCENDING_ORDER = "OPEN_DATE_ASCENDING_ORDER"
        const val OPEN_DATE_DESCENDING_ORDER = "OPEN_DATE_DESCENDING_ORDER"
    }
}

data class FilterDropDownMenuState(
    var day: Boolean = false,
    var score: Boolean = false,
    var openDate: Boolean = false,
)



