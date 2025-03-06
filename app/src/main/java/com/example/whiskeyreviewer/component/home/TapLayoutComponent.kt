package com.example.whiskeyreviewer.component.home

import android.annotation.SuppressLint
import android.app.ActionBar
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.whiskeyData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TapLayoutItems
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TapLayoutComponent(
    customFilter:@Composable () -> Unit,
    myReview: @Composable () -> Unit,
    updateCurrentPage:(TapLayoutItems) -> Unit,

) {

//    val whiskeyData=allWhiskyType
//    val whiskeyData = listOf(
//        TapLayoutItems.AllWhiskey,
//        TapLayoutItems.AmericanWhiskey,
//        TapLayoutItems.Blend,
//        TapLayoutItems.BlendedGrain,
//        TapLayoutItems.BlendedMalt,
//        TapLayoutItems.Bourbon,
//        TapLayoutItems.CanadianWhiskey,
//        TapLayoutItems.Corn,
//        TapLayoutItems.Rice,
//        TapLayoutItems.Rye,
//        TapLayoutItems.SingleGrain,
//        TapLayoutItems.SingleMalt,
//        TapLayoutItems.SinglePotStill,
//        TapLayoutItems.Spirit,
//        TapLayoutItems.Tennessee,
//        TapLayoutItems.Wheat
//    )


    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { whiskeyData.size })

    LaunchedEffect(pagerState.currentPage) {
        updateCurrentPage(whiskeyData[pagerState.currentPage])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            tabPositions[pagerState.currentPage],
                            60.dp
                        )
                        .graphicsLayer {
                            shape = RoundedCornerShape(
                                16.dp
                            )
                            clip = true
                        },
                    color = MainColor
                )
            }
        ) {
            whiskeyData.forEachIndexed { index, whiskey ->

                val textAndIconColor by animateColorAsState(
                    targetValue = if (pagerState.currentPage == index) LightBlackColor else Color.LightGray,
                    label = ""
                )

                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {

                        updateCurrentPage(whiskey)

                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier
                        .padding(top=4.dp,start=13.dp,end=16.dp,bottom=16.dp),
                    //잔상효과 제거
                    interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()

                        override suspend fun emit(interaction: Interaction) {}

                        override fun tryEmit(interaction: Interaction) = true
                    }
                ) {
                    Text(
                        text = whiskey.title,
                        style = TextStyle.Default.copy(
                            color = textAndIconColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }

        customFilter()


        HorizontalPager(state = pagerState) { page ->

            myReview()
        }

    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@Preview(showBackground = true)
@Composable
fun TapLayoutPreview() {

    val mainNavController= rememberNavController()

    WhiskeyReviewerTheme {
        TapLayoutComponent(
            myReview = {}, updateCurrentPage = {}, customFilter = {},
        )
    }
}
