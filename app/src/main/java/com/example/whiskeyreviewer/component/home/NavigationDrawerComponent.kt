package com.example.whiskeyreviewer.component.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.data.NavigationDrawerItems
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerComponent(drawerState: DrawerState, scope: CoroutineScope) {

    val mainNavController= rememberNavController()

    ModalDrawerSheet (
        modifier = Modifier
            .fillMaxWidth(0.8f),

        ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="위스키 리뷰어",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=8.dp)
            )

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                imageVector = ImageVector.vectorResource(R.drawable.menu_icon),
                contentDescription = "",
                tint = Color.Black,
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),

            ) {

            Text(
                text="위스키 검색",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                CustomSearchBoxComponent(
                    text="",
                    onValueChange = {},
                    search = { /*TODO*/ },
                    deleteInputText = {}
                )
            }

        }

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

    Column(Modifier
        .fillMaxWidth()
        .clickable(
            onClick = {
                Log.d("선택된 루트2", screen.screenRoute)

                when(screen.screenRoute){
                    NavigationDrawerItems.BACKUP -> {

                    }
                    else -> {
                        modalNavController.navigate(screen.screenRoute) {
                            popUpTo(modalNavController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                }
            },)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp)

                .height(50.dp),

            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.title,
                modifier = Modifier
                    .size(25.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = screen.title,
                style = TextStyle.Default.copy(
                    color = Color.Black,
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

    WhiskeyReviewerTheme {
        NavigationDrawerComponent(drawerState,scope)
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
