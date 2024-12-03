package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.CustomFilterRow
import com.example.whiskeyreviewer.component.home.MyReviewComponent
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.data.FloatingActionButtonItems
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                expendState = writeReviewViewModel.homeFloatingActionButtonState.value,
                floatingActionButtonClick = { writeReviewViewModel.toggleHomeFloatingActionButtonState() },
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
                    interactionSource = remember{ MutableInteractionSource() },
                    indication = null
                ) {
                    if(writeReviewViewModel.homeFloatingActionButtonState.value){
                        writeReviewViewModel.toggleHomeFloatingActionButtonState()
                    }
                },
            drawerState=drawerState,
            drawerContent = {
                NavigationDrawerComponent(drawerState=drawerState,scope=scope)
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

                            },
                            modifier=Modifier
                        )
                    },
                )

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



@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainNavController = rememberNavController()

    WhiskeyReviewerTheme {
        HomeView(writeReviewViewModel,mainNavController)
    }
}