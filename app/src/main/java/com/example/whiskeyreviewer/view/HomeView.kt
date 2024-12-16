package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customComponent.CustomFloatingActionButton
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.component.customComponent.RecentSearchWordComponent

import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.CustomFilterRow
import com.example.whiskeyreviewer.component.home.MyReviewComponent
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.data.FloatingActionButtonItems
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.RecentSearchWordManager
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mainViewModel.setRecentSearchTextList(
            recentSearchWordList = RecentSearchWordManager.loadRecentSearchList(context, type = RECENT_SEARCH_REVIEW_TEXT),
            type = RECENT_SEARCH_REVIEW_TEXT
        )
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                expendState = mainViewModel.homeFloatingActionButtonState.value,
                floatingActionButtonClick = { mainViewModel.toggleHomeFloatingActionButtonState() },
                floatingActionItemClick = {
                    when(it.screenRoute){
                        FloatingActionButtonItems.NewBottle.screenRoute-> {
                            Log.d("루트",it.screenRoute)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        FloatingActionButtonItems.NewBottle2.screenRoute-> {
                            Log.d("루트",it.screenRoute)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        else-> Log.d("루트",it.screenRoute)
                    }
                }
            )
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
                    titleTextValue = "나의 리뷰",
                    leftButton = {

                        CustomIconComponent(
                            icon = ImageVector.vectorResource(R.drawable.menu_icon),
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier
                        )
                    },
                    rightButton = {
                        CustomIconComponent(
                            icon = Icons.Default.Search,
                            onClick = {
                                mainViewModel.toggleHomeSearchBarState()
                            },
                            modifier=Modifier
                        )
                    },
                )

                if(mainViewModel.homeSearchBarState.value){

                    Text(
                        modifier = Modifier.padding(start=17.dp),
                        text="리뷰 검색",
                        style = TextStyle.Default.copy(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )

                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    CustomSearchBoxComponent(
                        text=mainViewModel.homeSearchBarSText.value,
                        onValueChange = {
                            mainViewModel.updateHomeSearchBarText(it)
                        },
                        search = {
                            mainViewModel.setRecentSearchTextList(
                                RecentSearchWordManager.saveSearchText(
                                    context = context,
                                    searchText="wfwfwf",
                                    type = RECENT_SEARCH_REVIEW_TEXT
                                ),
                                type = RECENT_SEARCH_REVIEW_TEXT
                            )
                        },
                        deleteInputText = {mainViewModel.updateHomeSearchBarText("")}
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(top=5.dp,bottom=5.dp,end=5.dp,start=7.dp),
                    ){

                        items(items = mainViewModel.recentSearchReviewTextList.value) { searchWord ->
                            if(searchWord!=""){
                                RecentSearchWordComponent(
                                    text=searchWord,
                                    deleteSearchWord = {
                                        mainViewModel.setRecentSearchTextList(
                                            RecentSearchWordManager.deleteRecentSearchText(
                                                context = context,
                                                searchText=searchWord,
                                                type= RECENT_SEARCH_REVIEW_TEXT
                                            ),
                                            type = RECENT_SEARCH_REVIEW_TEXT
                                        )

                                    },
                                    search = {

                                    }
                                )
                            }
                        }
                    }
                }

                TapLayoutComponent(
                    customFilter = {
                        CustomFilterRow(mainViewModel = mainViewModel)
                    },
                    myReview = {
                        MyReviewComponent(
                            myReviewItems = mainViewModel.myReviewList.value,
                            setSelectReview = {singleWhiskyData->
                                mainViewModel.updateSelectReview(singleWhiskyData)
                                navController.navigate(MainRoute.WHISKY_DETAIL)
                            }
                        )
                    },
                    updateCurrentPage = {
                        mainViewModel.getFilteredWhiskeyReview(it)
                    },

                )

            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainNavController = rememberNavController()

    WhiskeyReviewerTheme {
        HomeView(writeReviewViewModel, mainNavController, mainViewModel)
    }
}