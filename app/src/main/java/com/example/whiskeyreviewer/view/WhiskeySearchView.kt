package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.component.customComponent.EmptyMyWhiskyReviewComponent
import com.example.whiskeyreviewer.component.customComponent.RecentSearchWordComponent
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailDropDownMenuComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.ImageViewerDialog
import com.example.whiskeyreviewer.component.home.MyReviewComponent
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.component.home.WhiskyCustomFilterRow
import com.example.whiskeyreviewer.component.myReview.MyReviewPost
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.utils.RecentSearchWordManager
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun WhiskeySearchView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        mainViewModel.toggleDrawerSearchBarState(state = true)
//        writeReviewViewModel.updateDrawerSearchBarText(writeReviewViewModel.drawerSearchBarText.value)
    }

    Scaffold(
        floatingActionButton = {
//            CustomFloatingActionButton(
//                expendState = writeReviewViewModel.homeFloatingActionButtonState.value,
//                floatingActionButtonClick = { writeReviewViewModel.toggleHomeFloatingActionButtonState() },
//                floatingActionItemClick = {
//                    when(it.screenRoute){
//                        FloatingActionButtonItems.NewBottle.screenRoute-> {
//                            Log.d("루트",it.screenRoute)
//                            navController.navigate(MainRoute.INSERT_REVIEW)
//                        }
//                        FloatingActionButtonItems.NewBottle2.screenRoute-> {
//                            Log.d("루트",it.screenRoute)
//                            navController.navigate(MainRoute.INSERT_REVIEW)
//                        }
//                        else-> Log.d("루트",it.screenRoute)
//                    }
//                }
//            )
        }
    ) {
        ModalNavigationDrawer(
            modifier = Modifier
                //흐리게 만들기는 일단 보류
//                .blur(
//                    if (writeReviewViewModel.homeFloatingActionButtonState.value) 6.dp else 0.dp
//                )
                .padding(it)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (mainViewModel.homeFloatingActionButtonState.value) {
                        mainViewModel.toggleHomeFloatingActionButtonState()
                    }
                },
            drawerState=drawerState,
            drawerContent = {
                NavigationDrawerComponent(drawerState=drawerState,scope=scope,mainViewModel=mainViewModel,navController=navController)
            })
        {

            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                CustomAppBarComponent(
                    titleTextValue = "위스키 검색",
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
                        CustomIconComponent(
                            icon = Icons.Default.Search,
                            onClick = {
                                mainViewModel.toggleDrawerSearchBarState()
                            },
                            modifier= Modifier
                        )
                    },
                )

                if(mainViewModel.whiskeySearchBarState.value){

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text="위스키 검색",
                            style = TextStyle.Default.copy(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.padding(start=20.dp,)
                        )

                        Text(
                            text="세부 검색",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .drawBehind {
                                    val strokeWidthPx = 2.dp.toPx()
                                    val verticalOffset = size.height - 1.sp.toPx()
                                    drawLine(
                                        color = Color.LightGray,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticalOffset),
                                        end = Offset(size.width, verticalOffset)
                                    )
                                }
                                .clickable {
                                    mainViewModel.toggleDetailSearchDialogState()
                                }
                        )

                    }

                    CustomSearchBoxComponent(
                        text=mainViewModel.otherUserWhiskySearchText.value.searchText,
                        onValueChange = {
                            mainViewModel.updateDrawerSearchBarText(it)
                        },
                        search = {
                            mainViewModel.setRecentSearchTextList(
                                RecentSearchWordManager.saveSearchText(
                                    context = context,
                                    searchText=mainViewModel.otherUserWhiskySearchText.value.searchText,
                                    type = RECENT_SEARCH_WHISKEY_TEXT
                                ),
                                type = RECENT_SEARCH_WHISKEY_TEXT
                            )
                        },
                        deleteInputText = {mainViewModel.updateDrawerSearchBarText("")}
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(top=5.dp,bottom=12.dp,end=5.dp,start=7.dp),
                    ){

                        items(items = mainViewModel.recentSearchWhiskeyTextList.value) { searchWord ->
                            if(searchWord!=""){
                                RecentSearchWordComponent(
                                    text=searchWord,
                                    deleteSearchWord = {
                                        mainViewModel.setRecentSearchTextList(
                                            RecentSearchWordManager.deleteRecentSearchText(
                                                context = context,
                                                searchText=searchWord,
                                                type= RECENT_SEARCH_WHISKEY_TEXT
                                            ),
                                            type = RECENT_SEARCH_WHISKEY_TEXT
                                        )

                                    },
                                    search = {

                                    }
                                )
                            }else{
                                Spacer(modifier = Modifier.height(35.dp))
                            }
                        }
                    }
                }

                Column(
                    Modifier.fillMaxWidth()
                ) {
                    WhiskyCustomFilterRow(mainViewModel = mainViewModel)

                    if(mainViewModel.reviewList.value.isEmpty()){
                        Log.d("리스트", mainViewModel.reviewList.value.toString())
                        EmptyMyWhiskyReviewComponent(
                            text="해당 위스키가 존재하지 않습니다."
                        )
                    }else {
                        MyReviewComponent(
                            myReviewItems = mainViewModel.reviewList.value,
                            setSelectReview = { singleWhiskyData ->
                                mainViewModel.updateOderUserSelectReview(singleWhiskyData)
                                navController.navigate(MainRoute.OTHER_USER_REVIEW_DETAIL)
                            },
                            toggleConfirmDialogState = {},
                            showOption = false
                        )
                    }
                }

            }
        }
    }

}

@Composable
fun OtherUserReviewDetailView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scrollState= rememberScrollState()

    ImageViewerDialog(
        currentImage = mainViewModel.selectImageUrl.value,
        toggleOption = { mainViewModel.toggleImageDialogState() },
        currentState = mainViewModel.imageDialogState.value
    )


    Scaffold(
        floatingActionButton = {

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
                titleTextValue = "위스키 검색",
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
                reviewClick = {},
                deleteWhisky = {},
                showOption = false
            )


            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, top = 8.dp),
                horizontalArrangement = Arrangement.End
            ){

                WhiskeyDetailDropDownMenuComponent(

                    value = mainViewModel.currentMyReviewDayFilter.value,
                    onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                    dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.day,
                    toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                        MyReviewFilterItems.DAY) },
                    menuItems = listOf(MyReviewFilterItems.New, MyReviewFilterItems.Old,)
                )

                WhiskeyDetailDropDownMenuComponent(

                    value = mainViewModel.currentMyReviewVoteFilter.value,
                    onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                    dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.vote,
                    toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                        MyReviewFilterItems.VOTE) },
                    menuItems = listOf(MyReviewFilterItems.Best, MyReviewFilterItems.Worst,)
                )
            }


                when (mainViewModel.currentMyReviewTypeFilter.value) {

                    MyReviewFilterItems.Review -> {
                        Column(
                            modifier = Modifier
                                .height(1000.dp)
                        ) {
                            MyReviewPost(
                                singleReviewClick = {
                                    mainViewModel.setSelectReviewData(it)
                                    navController.navigate(MainRoute.REVIEW_DETAIL)
                                },
                                modifyAllow = false,
                                reviewDataList = mainViewModel.myReviewDataList.value,
                                onImageSelect = {
                                    mainViewModel.setSelectImage(it)
                                    mainViewModel.toggleImageDialogState()
                                },

                                )
                        }
                    }

                    MyReviewFilterItems.New -> TODO()
                    MyReviewFilterItems.Old -> TODO()
                    MyReviewFilterItems.Best -> TODO()
                    MyReviewFilterItems.Worst -> TODO()
                    MyReviewFilterItems.Graph -> TODO()
                }


        }

    }

}
