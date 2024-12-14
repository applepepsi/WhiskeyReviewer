package com.example.whiskeyreviewer.component.home

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.RECENT_SEARCH_REVIEW_TEXT
import com.example.nextclass.utils.RECENT_SEARCH_WHISKEY_TEXT
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.component.customComponent.RecentSearchWordComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.NavigationDrawerItems
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.RecentSearchWordManager
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerComponent(
    drawerState: DrawerState,
    scope: CoroutineScope,
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val mainNavController= rememberNavController()

    LaunchedEffect(Unit) {
        writeReviewViewModel.setRecentSearchTextList(
            recentSearchWordList = RecentSearchWordManager.loadRecentSearchList(context, type = RECENT_SEARCH_WHISKEY_TEXT),
            type = RECENT_SEARCH_WHISKEY_TEXT
        )

    }

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

            Text(
                text="위스키 검색",
                style = TextStyle.Default.copy(
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(start=20.dp,bottom=5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                CustomSearchBoxComponent(
                    text=writeReviewViewModel.drawerSearchBarText.value,
                    onValueChange = {
                        writeReviewViewModel.updateDrawerSearchBarText(it)
                    },
                    search = {
                        writeReviewViewModel.setRecentSearchTextList(
                            RecentSearchWordManager.saveSearchText(
                                context = context,
                                searchText=writeReviewViewModel.drawerSearchBarText.value,
                                type = RECENT_SEARCH_WHISKEY_TEXT
                            ),
                            type = RECENT_SEARCH_WHISKEY_TEXT
                        )

                        navController.navigate(MainRoute.WHISKEY_SEARCH)
                    },
                    deleteInputText = {writeReviewViewModel.updateDrawerSearchBarText("") }
                )
            }
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
                                writeReviewViewModel.updateDrawerSearchBarText(searchWord)
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
            navController = mainNavController,
            navigationDrawerController = mainNavController)

    }
}


@Composable
fun NavigationDrawerItemsComponent(
    navController: NavHostController,
    navigationDrawerController: NavHostController,

    ) {
    val screens = listOf(
        NavigationDrawerItems.Setting,
        NavigationDrawerItems.Backup,
    )

    val currentDestination = navigationDrawerController.currentBackStackEntryAsState().value?.destination

    Column {
        Spacer(modifier = Modifier.height(15.dp))

        Surface(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(modifier = Modifier) {
                items(screens){singleItem->
                    NavigationDrawerItem(
                        screen = singleItem,
                        currentDestination = currentDestination,
                        modalNavController = navigationDrawerController,
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
    modalNavController: NavHostController,

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

                        }

                        else -> {
                            modalNavController.navigate(screen.screenRoute) {
                                popUpTo(modalNavController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
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
            Icon(
                imageVector = ImageVector.vectorResource(screen.icon),
                contentDescription = screen.title,
                modifier = Modifier
                    .size(25.dp),
                tint = LightBlackColor
            )

            Spacer(modifier = Modifier.width(15.dp))

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
    val navHostController= rememberNavController()
    WhiskeyReviewerTheme {
        NavigationDrawerComponent(drawerState,scope,writeReviewViewModel,navHostController)
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationDrawerItemsPreview() {

    val mainNavController= rememberNavController()

    WhiskeyReviewerTheme {
        NavigationDrawerItemsComponent(mainNavController,mainNavController)
    }
}
