package com.example.whiskeyreviewer.component.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.LiveSearchBoxComponent
import com.example.whiskeyreviewer.component.customComponent.RecentSearchWordComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.dialog.DetailSearchDialog
import com.example.whiskeyreviewer.component.dialog.InsertBackupCodeDialog
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.NavigationDrawerItems
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.RecentSearchWordManager
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerComponent(
    drawerState: DrawerState,
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current



    LaunchedEffect(Unit) {
        mainViewModel.setRecentSearchTextList(
            recentSearchWordList = RecentSearchWordManager.loadRecentSearchList(context, type = RECENT_SEARCH_WHISKEY_TEXT),
            type = RECENT_SEARCH_WHISKEY_TEXT
        )
    }



    InsertBackupCodeDialog(
        toggleOption = { mainViewModel.toggleDrawerDialogState(NavigationDrawerItems.InsertBackupCode) },
        currentState = mainViewModel.insertBackupCodeDialogState.value,
        submitBackupCode = { mainViewModel.submitBackupCode(it) },
        submitResult = mainViewModel.backupCodeResult.value,
        resetResult= {
            mainViewModel.resetSubmitBackupCodeResultState()
        }
    )


    DetailSearchDialog(
        updateText = { mainViewModel.updateDetailSearchWordText(it) },
        text=mainViewModel.reviewFilterData.value.detailSearchText,
        toggleOption = {mainViewModel.toggleDetailSearchDialogState()},
        currentState = mainViewModel.detailSearchDialogState.value
        )

    ModalDrawerSheet (
        modifier = Modifier
            .fillMaxWidth(0.8f),

        ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 20.dp, top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="위스키 리뷰어",
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=8.dp)
            )

            CustomIconComponent(
                icon = ImageVector.vectorResource(R.drawable.menu_icon),
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                },
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text="전체 리뷰 검색",
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

//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ){
//                CustomSearchBoxComponent(
//                    text=mainViewModel.reviewFilterData.value.searchText,
//                    onValueChange = {
//                        mainViewModel.updateDrawerSearchBarText(it)
//                    },
//                    search = {
//                        mainViewModel.setRecentSearchTextList(
//                            RecentSearchWordManager.saveSearchText(
//                                context = context,
//                                searchText=mainViewModel.reviewFilterData.value.searchText,
//                                type = RECENT_SEARCH_WHISKEY_TEXT
//                            ),
//                            type = RECENT_SEARCH_WHISKEY_TEXT
//                        )
//                        //검색
//
//                        navController.navigate(MainRoute.WHISKEY_SEARCH)
//                    },
//                    deleteInputText = {mainViewModel.updateDrawerSearchBarText("") },
//                    liveSearch = true,
//                    liveSearchDataList = mainViewModel.liveSearchDataList.value
//                )
//            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                LiveSearchBoxComponent(
                    text=mainViewModel.reviewFilterData.value.searchText,
                    onValueChange = {
                        mainViewModel.updateDrawerSearchBarText(it)
                        mainViewModel.getLiveSearchData(it)
                    },
                    search = {
                        mainViewModel.setRecentSearchTextList(
                            RecentSearchWordManager.saveSearchText(
                                context = context,
                                searchText=mainViewModel.reviewFilterData.value.searchText,
                                type = RECENT_SEARCH_WHISKEY_TEXT
                            ),
                            type = RECENT_SEARCH_WHISKEY_TEXT
                        )
                        mainViewModel.getSearchReviewData()
                        navController.navigate(MainRoute.WHISKEY_SEARCH)
                    },
                    deleteInputText = {mainViewModel.updateDrawerSearchBarText("")},
                    liveSearchDataList = mainViewModel.liveSearchDataList.value,
                    onLiveSearchDataClick = {
                        mainViewModel.updateDrawerSearchBarText(it)

                    },
                    liveSearchDropDownState=mainViewModel.liveSearchDropDownOpenState.value,
                    toggleLiveSearchDropDownMenuState={state->
                        mainViewModel.toggleLiveSearchOpenState(state)
                    },
                )
            }

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
                                mainViewModel.updateDrawerSearchBarText(searchWord)
                                navController.navigate(MainRoute.WHISKEY_SEARCH)
                            }
                        )
                    }else{
                        Spacer(modifier = Modifier.height(37.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        NavigationDrawerItemsComponent(
            navController = navController,

            toggleDialogState = {mainViewModel.toggleDrawerDialogState(it)},

        )

    }
}


@Composable
fun NavigationDrawerItemsComponent(
    navController: NavHostController,

    toggleDialogState:(NavigationDrawerItems)->Unit,

    ) {
    val screens = listOf(
        NavigationDrawerItems.Setting,
        NavigationDrawerItems.Backup,
        NavigationDrawerItems.InsertBackupCode
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Column {
        Spacer(modifier = Modifier.height(15.dp))

        Surface(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier) {
                items(screens){singleItem->
                    NavigationDrawerItem(
                        screen = singleItem,
                        currentDestination = currentDestination,
                        navController=navController,
                        toggleDialogState = {toggleDialogState(it)}
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationDrawerItem(
    screen: NavigationDrawerItems,
    currentDestination: NavDestination?,
    navController: NavHostController,
    toggleDialogState:(NavigationDrawerItems)->Unit
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.screenRoute } == true
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    Log.d("선택된 루트2", screen.screenRoute)

                    when (screen.screenRoute) {
                        NavigationDrawerItems.BACKUP -> {
                            toggleDialogState(NavigationDrawerItems.Backup)
                        }

                        NavigationDrawerItems.INSERT_BACKUP_CODE -> {
                            toggleDialogState(NavigationDrawerItems.InsertBackupCode)
                        }

                        NavigationDrawerItems.SETTING -> {
                            navController.navigate(MainRoute.SETTING_VIEW)
                        }
                    }
                },
            )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp)

                .height(60.dp),

            verticalAlignment = Alignment.CenterVertically,

            ) {
//            Icon(
//                imageVector = ImageVector.vectorResource(screen.icon),
//                contentDescription = screen.title,
//                modifier = Modifier
//                    .size(25.dp),
//                tint = LightBlackColor
//            )
            CustomIconComponent(
                icon = ImageVector.vectorResource(screen.icon),
                onClick = { /*TODO*/ },
                modifier = Modifier.size(32.dp),
                iconSize = Modifier.size(20.dp),
                onClickAllow = false
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = screen.title,
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
            )
        }
        NavigationDrawerLabel(selectColor = Color.LightGray, modifier = Modifier.padding(horizontal = 15.dp))

    }
}
@Composable
fun NavigationDrawerLabel(
    selectColor: Color,
    modifier: Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
//            .padding(start = 15.dp, end = 15.dp)
            .background(selectColor)
            .height(0.5.dp)

    )
}



@Preview(showBackground = true)
@Composable
fun ModalNavPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel:MainViewModel= hiltViewModel()
    val navHostController= rememberNavController()
    WhiskeyReviewerTheme {
        NavigationDrawerComponent(drawerState,scope,mainViewModel,navHostController)
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationDrawerItemsPreview() {

    val mainNavController= rememberNavController()

    WhiskeyReviewerTheme {

    }
}
