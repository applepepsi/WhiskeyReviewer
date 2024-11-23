package com.example.whiskeyreviewer.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
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
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.home.NavigationDrawerComponent
import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import kotlinx.coroutines.launch


@Composable
fun HomeView(

) {
    val testData= listOf(
        SingleWhiskeyData(), SingleWhiskeyData(),SingleWhiskeyData()
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState=drawerState,
        drawerContent = {
            NavigationDrawerComponent(drawerState=drawerState,scope=scope)
        })
    {
        Column(
            modifier=Modifier
                .fillMaxSize()
        ) {

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                imageVector = ImageVector.vectorResource(R.drawable.menu_icon),
                contentDescription = "",
                tint = Color.Black,
            )

            LazyColumn(

                horizontalAlignment = Alignment.CenterHorizontally,
//            contentPadding = PaddingValues(vertical = 10.dp),
            ) {
                item{

                }

                items(items = testData){ singleWhiskeyData->
                    SingleWhiskeyComponent(singleWhiskeyData = singleWhiskeyData)

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
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