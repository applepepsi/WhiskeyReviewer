package com.example.whiskeyreviewer.component.customComponent

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.data.FloatingActionButtonItems
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.OrangeColor




@Composable
fun CustomFloatingActionButton(
    expendState:Boolean,
    floatingActionButtonClick:()->Unit,
    floatingActionItemClick:(FloatingActionButtonItems)->Unit,
) {


    val floatingItems = listOf(
        FloatingActionButtonItems.NewBottle,
        FloatingActionButtonItems.NewBottle2
    )
    Column(
        horizontalAlignment = Alignment.End
    ) {
        //애니메이션
        AnimatedVisibility(
            visible = expendState,
            //화면에 나타날때 + fadeIn 서서히 나타나는 효과 +
            // slideInVertically 아래에서 위로 나타남 +
            // expandVertically 수직확장 나타남
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            //화면에 사라질때 + fadeout 서서히 사라지는 효과
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(
                modifier= Modifier.padding(bottom = 8.dp)
            ) {
                items(items=floatingItems) {floatingItem->
                    SingleFloatingActionButtonItem(
                        icon = ImageVector.vectorResource(floatingItem.icon),
                        title = floatingItem.title,
                        onClick = {
                            Log.d("터치", floatingItem.toString())
                            floatingActionItemClick(floatingItem)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expendState, label = "")
        val rotation by transition.animateFloat(label = "") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = {
                floatingActionButtonClick()
                },
            containerColor = OrangeColor,
            modifier=Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "",
                modifier = Modifier.rotate(rotation).size(30.dp)
            )
        }
    }
}

@Composable
fun SingleFloatingActionButtonItem(
    icon: ImageVector,
    title: String,
    onClick:()->Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier

    ) {

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .border(0.5.dp, Color.LightGray, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .padding(8.dp)

        ) {
            Text(
                text = title,
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        FloatingActionButton(
            onClick = {
                onClick()
            },
            modifier = Modifier.size(40.dp),
            containerColor = OrangeColor
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",

                modifier = Modifier.size(20.dp)

            )
        }
    }
}
