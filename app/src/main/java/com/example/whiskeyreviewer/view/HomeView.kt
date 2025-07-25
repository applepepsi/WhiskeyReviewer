package com.example.whiskeyreviewer.view

import android.content.Intent
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
import com.example.whiskeyreviewer.component.customComponent.EmptyMyWhiskyReviewComponent
import com.example.whiskeyreviewer.component.customComponent.PostProgressIndicator
import com.example.whiskeyreviewer.component.customComponent.RecentSearchWordComponent

import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.MyWhiskyComponent
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.dialog.SelectWhiskeyDialog
import com.example.whiskeyreviewer.component.home.MyWhiskyCustomFilterRow
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


    LaunchedEffect(mainViewModel.whiskyDeleteState.value) {
        if(mainViewModel.whiskyDeleteState.value){
            mainViewModel.toggleWhiskyDeleteState(state = false)
        }
    }



    LaunchedEffect(Unit) {
        mainViewModel.setRecentSearchTextList(
            recentSearchWordList = RecentSearchWordManager.loadRecentSearchList(context, type = RECENT_SEARCH_REVIEW_TEXT),
            type = RECENT_SEARCH_REVIEW_TEXT
        )
    }

    LaunchedEffect(mainViewModel.selectWhiskyState.value) {
        if(mainViewModel.selectWhiskyState.value){
            navController.navigate(MainRoute.WHISKY_DETAIL)
            mainViewModel.toggleSelectWhiskyState(state=false)
        }
    }

    LaunchedEffect(mainViewModel.backupCodeResult.value) {
        Log.d("백업 결과", mainViewModel.backupCodeResult.value.toString())
        if(mainViewModel.backupCodeResult.value == true){

            val intent=context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.let {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(intent)
                Runtime.getRuntime().exit(0)
            }

        }
    }


    SelectWhiskeyDialog(
        toggleOption = { mainViewModel.toggleWhiskySelectDialogState() },
        currentState = mainViewModel.selectWhiskyDialogState.value,
        text=mainViewModel.selectWhiskyText.value,
        submitWhiskey = {
            //새로운 위스키 등록에 성공했다면 새로운 첫번째 병으로 자동 등록
            // 기존 위스키라면 기존 위스키의 디테일뷰로 이동
//            mainViewModel.submitNewWhiskey()

        },
        updateText = {
            mainViewModel.updateWhiskySearchText(it)
            mainViewModel.whiskySearch()
            },
        searchWhisky={
            mainViewModel.whiskySearch()
                     },
        mainViewModel = mainViewModel,
        toggleInsertDetailDialog = {

            mainViewModel.selectNewWhiskyInfoMode()
        },
        selfInput = {
            mainViewModel.selfInputWhiskyMode()
        }
    )

    //커스텀 위스키 추가 하는 다이얼로그 일단 보류



    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                expendState = mainViewModel.homeFloatingActionButtonState.value,
                floatingActionButtonClick = {
                    mainViewModel.toggleHomeFloatingActionButtonState() },

                floatingActionItemClick = {
                    when(it){
//                        FloatingActionButtonItems.CustomWhiskey -> {
//                            mainViewModel.toggleCustomWhiskySelectDialogState()
//                        }
                        FloatingActionButtonItems.NewWhiskey -> {
                            mainViewModel.toggleWhiskySelectDialogState()
                        }
                        else->{

                        }
                    }

                },
                items = listOf(
                    FloatingActionButtonItems.NewWhiskey,
//                    FloatingActionButtonItems.CustomWhiskey,
                )
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
                            mainViewModel.myWhiskySearch()
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
                                        mainViewModel.updateHomeSearchBarText(searchWord)
                                        mainViewModel.getMyWhiskeyData()
                                    }
                                )
                            }
                        }
                    }
                }

                TapLayoutComponent(
                    customFilter = {
                                   //필터를 리뷰와 하나로 통합시켜서 스크롤 했을 때 같이 내려가도록 수정
                        MyWhiskyCustomFilterRow(mainViewModel = mainViewModel)
                    },
                    myReview = {

                        if(mainViewModel.postProgressIndicatorState.value){
                            PostProgressIndicator()
                        }else{
                            if(mainViewModel.myWhiskyList.value.isEmpty()){
                                EmptyMyWhiskyReviewComponent(
                                    text="작성된 리뷰가 없습니다."
                                )
                            }else{


                                MyWhiskyComponent(
                                    myReviewItems = mainViewModel.myWhiskyList.value,
                                    setSelectReview = {singleWhiskyData->
                                        mainViewModel.updateSelectWhisky(singleWhiskyData)
                                        mainViewModel.toggleSelectWhiskyState(state = true)
//                                    navController.navigate(MainRoute.WHISKY_DETAIL)
                                    },
                                    toggleConfirmDialogState = {
                                        mainViewModel.updateSelectWhisky(it)
                                        mainViewModel.toggleDeleteWhiskyConfirmDialog()
                                    },
                                    dropDownMenuState = mainViewModel.whiskyOptionDropDownMenuState,
                                    toggleDropDownMenuState = { index->
                                        mainViewModel.toggleWhiskyOptionDropDownMenuState(index)
                                    },
                                    mainViewModel=mainViewModel
                                )

                            }
                        }
                    },
                    updateCurrentPage = {
                        mainViewModel.updateMyWhiskyType(it)
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