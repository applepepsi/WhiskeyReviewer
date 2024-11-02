package com.example.whiskeyreviewer.view.toolBar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme


@Composable
fun InsertReviewToolBarComponent() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp)
        .padding(bottom=5.dp)
        .clip(shape = RoundedCornerShape(15.dp))

        ) {

        Row(modifier = Modifier) {
            ToolBarItems.items.forEach { item ->
                AddInsertReviewToolBarItem(item)
            }
        }



    }
}

@Composable
fun RowScope.AddInsertReviewToolBarItem(item: ToolBarItems) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
//    val backgroundColor = if (isPressed) Color.Red else Color.Black
//    val contentColor = if (isPressed) Color.Blue else Color.Yellow
    val contentColor = Color.White
    val backgroundColor = MainColor
    Column(
        modifier = Modifier

            .weight(1f)
            .clickable(
                onClick = { item.action() },
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            .background(backgroundColor)
            .padding(vertical = 7.dp,),
        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = contentColor,
            modifier = Modifier.size(25.dp)
        )

        Text(
            text = item.title,
            style = TextStyle.Default.copy(color = contentColor, fontSize = 13.sp, fontWeight = FontWeight.Normal),
            modifier = Modifier
        )

    }
}


@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    WhiskeyReviewerTheme {
        InsertReviewToolBarComponent()
    }
}