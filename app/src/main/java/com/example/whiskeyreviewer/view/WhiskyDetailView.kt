package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customComponent.CustomFloatingActionButton
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailBottleNumDropDownMenuComponent
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailDropDownMenuComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.component.myReview.MyReviewGraphComponent2
import com.example.whiskeyreviewer.component.myReview.MyReviewPost
import com.example.whiskeyreviewer.data.FloatingActionButtonItems
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.MainRoute.REVIEW_DETAIL
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import java.time.LocalDate

@Composable
fun WhiskeyDetailView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scrollState= rememberScrollState()

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                expendState = mainViewModel.homeFloatingActionButtonState.value,
                floatingActionButtonClick = { mainViewModel.toggleHomeFloatingActionButtonState() },
                floatingActionItemClick = {
                    //이름 수정해야함
                    val info=WhiskyName(
                        whisky_name = "test",
                        is_first = false,
                        whisky_uuid = ""
                    )
                    when(it.screenRoute){
                        FloatingActionButtonItems.OldBottle.screenRoute-> {
                            Log.d("루트",it.screenRoute)


                            mainViewModel.setWriteReviewWhiskyInfo(info, bottleNum = mainViewModel.currentMyReviewBottleNumFilter.value)

//                            mainViewModel.setCurrentBottleNum(mainViewModel.currentMyReviewBottleNumFilter.value)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        FloatingActionButtonItems.NewBottle.screenRoute-> {
                            Log.d("루트",it.screenRoute)
                            mainViewModel.setWriteReviewWhiskyInfo(info, bottleNum = mainViewModel.currentMyReviewBottleNumFilter.value+1)

//                            mainViewModel.setCurrentBottleNum(mainViewModel.currentMyReviewBottleNumFilter.value+1)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        else-> Log.d("루트",it.screenRoute)
                    }
                },
                items=
                    listOf(
                        FloatingActionButtonItems.OldBottle,
                        FloatingActionButtonItems.NewBottle
                    )

            )
        }
    ) {
    Column(
        modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {

        CustomAppBarComponent(
            titleTextValue = "나의 리뷰",
            leftButton = {
                CustomIconComponent(
                    icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                )
            },
            rightButton = {
                Spacer(modifier = Modifier.size(35.dp))
            },
        )
        SingleWhiskeyComponent(
            singleWhiskeyData = SingleWhiskeyData(),
            reviewClick = {}
        )


        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 8.dp),
            horizontalArrangement = Arrangement.End
        ){

            WhiskeyDetailBottleNumDropDownMenuComponent(

                value = mainViewModel.currentMyReviewBottleNumFilter.value,
                onValueChange = { mainViewModel.updateMyBottleNumFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.bottleNum,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                    MyReviewFilterItems.BOTTLE_NUM) },
                menuItems = (1..mainViewModel.myReviewData.value.bottleCount).toList()
            )

            Spacer(modifier = Modifier.width(10.dp))

            WhiskeyDetailDropDownMenuComponent(

                value = mainViewModel.currentMyReviewDayFilter.value,
                onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.day,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(MyReviewFilterItems.DAY) },
                menuItems = listOf(MyReviewFilterItems.New,MyReviewFilterItems.Old,)
            )

            Spacer(modifier = Modifier.width(10.dp))

            WhiskeyDetailDropDownMenuComponent(

                value = mainViewModel.currentMyReviewTypeFilter.value,
                onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.reviewType,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(MyReviewFilterItems.REVIEW_TYPE) },
                menuItems = listOf(MyReviewFilterItems.Review,MyReviewFilterItems.Graph)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            when (mainViewModel.currentMyReviewTypeFilter.value) {
                MyReviewFilterItems.Graph -> {
                    MyReviewGraphComponent2(
                        mainViewModel.myReviewDataList.value
                    )
                }

                MyReviewFilterItems.Review -> {
                    MyReviewPost(
                        singleReviewClick = {
                            mainViewModel.setSelectReviewData(it)
                            navController.navigate(REVIEW_DETAIL)
                        }
                    )
                }

                MyReviewFilterItems.New -> TODO()
                MyReviewFilterItems.Old -> TODO()
            }

            }
        }

    }

}

val testReviewDataList = listOf(
    WriteReviewData(
        content = "스타일 A",
        is_anonymous = false,
        open_date = LocalDate.of(2024, 1, 10),
        tags = listOf(""),
        score = 4.0
    ),
    WriteReviewData(
        content = "스타일 B",
        is_anonymous = true,
        open_date = LocalDate.of(2025, 2, 15),
        tags = listOf(""),
        score = 3.0
    ),
    WriteReviewData(
        content = "스타일 C",
        is_anonymous = false,
        open_date = LocalDate.of(2022, 3, 20),
        tags = listOf(""),
        score = 5.0
    ),
    WriteReviewData(
        content = "스타일 D",
        is_anonymous = true,
        open_date = LocalDate.of(2021, 4, 5),
        tags = listOf(""),
        score = 2.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 4.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 4.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 2.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 3.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 2.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 5.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 3.0
    ),
    WriteReviewData(
        content = "스타일 E",
        is_anonymous = false,
        open_date = LocalDate.of(2020, 5, 30),
        tags = listOf(""),
        score = 1.0
    ),
)

@Preview(showBackground = true)
@Composable
fun WhiskeyDetailPreview() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel:MainViewModel= hiltViewModel()
    val mainNavController = rememberNavController()
    WhiskeyReviewerTheme {
        WhiskeyDetailView(writeReviewViewModel, mainNavController, mainViewModel)
    }
}