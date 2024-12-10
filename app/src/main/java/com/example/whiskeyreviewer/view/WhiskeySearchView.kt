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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
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
import com.example.whiskeyreviewer.utils.RecentSearchWordManager
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.launch

@Composable
fun WhiskeySearchView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


//    LaunchedEffect(Unit) {
//        writeReviewViewModel.updateHomeSearchBarText(it)
//    }

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
                    if (writeReviewViewModel.homeFloatingActionButtonState.value) {
                        writeReviewViewModel.toggleHomeFloatingActionButtonState()
                    }
                },
            drawerState=drawerState,
            drawerContent = {
                NavigationDrawerComponent(drawerState=drawerState,scope=scope,writeReviewViewModel=writeReviewViewModel,navController=navController)
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
                                writeReviewViewModel.toggleHomeSearchBarState()
                            },
                            modifier= Modifier
                        )
                    },
                )

                if(writeReviewViewModel.whiskeySearchBarState.value){

                    Text(
                        modifier = Modifier.padding(start=17.dp),
                        text="위스키 검색",
                        style = TextStyle.Default.copy(
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    CustomSearchBoxComponent(
                        text=writeReviewViewModel.homeSearchBarSText.value,
                        onValueChange = {
                            writeReviewViewModel.updateDrawerSearchBarText(it)
                        },
                        search = {
                            writeReviewViewModel.setRecentSearchTextList(
                                RecentSearchWordManager.saveSearchText(
                                    context = context,
                                    searchText="wfwfwf",
                                    type = RECENT_SEARCH_WHISKEY_TEXT
                                ),
                                type = RECENT_SEARCH_WHISKEY_TEXT
                            )
                        },
                        deleteInputText = {writeReviewViewModel.updateDrawerSearchBarText("")}
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(top=5.dp,bottom=12.dp,end=5.dp,start=7.dp),
                    ){

                        items(items = writeReviewViewModel.recentSearchWhiskeyTextList.value) { searchWord ->
                            if(searchWord!=""){
                                RecentSearchWordComponent(
                                    text=searchWord,
                                    deleteSearchWord = {
                                        writeReviewViewModel.setRecentSearchTextList(
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

                TapLayoutComponent(
                    customFilter = {
                        CustomFilterRow(writeReviewViewModel = writeReviewViewModel)
                    },
                    myReview = {
                        MyReviewComponent(
                            myReviewItems = writeReviewViewModel.myReviewList.value,
                            setSelectReview = {singleWhiskyData->
                                writeReviewViewModel.updateSelectReview(singleWhiskyData)
                                navController.navigate(MainRoute.WHISKY_DETAIL)
                            }
                        )
                    },
                    updateCurrentPage = {
                        writeReviewViewModel.getFilteredWhiskeyReview(it)
                    },

                    )

            }
        }
    }

}
