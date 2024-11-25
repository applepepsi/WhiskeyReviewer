package com.example.whiskeyreviewer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.MainTitleComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.CustomFilterRow
import com.example.whiskeyreviewer.component.home.MyReviewComponent
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeView(

) {

    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
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

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).padding(top=10.dp,bottom=5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.menu_icon),
                    contentDescription = "",
                    tint = Color.Black,
                )

                MainTitleComponent(
                    value = "나의 리뷰"
                )

                CustomIconComponent(
                    icon = Icons.Default.Search,
                    onClick = {

                    },
                    modifier=Modifier
                )


            }


            TapLayoutComponent(
                customFilter = {
                    CustomFilterRow(writeReviewViewModel = writeReviewViewModel)
                },
                myReview = {
                    MyReviewComponent(
                        myReviewItems = writeReviewViewModel.myReviewList.value
                    )
                },
                updateCurrentPage = { writeReviewViewModel.getFilteredWhiskeyReview(it) }
            )
        }
    }


}



@Preview(showBackground = true)
@Composable
fun HomePreview() {


    WhiskeyReviewerTheme {
        HomeView()
    }
}