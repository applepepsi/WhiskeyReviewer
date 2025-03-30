package com.example.whiskeyreviewer.component.customComponent

import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor

@Composable
fun PrivateCheckboxComponent(
    checked:Boolean,
    onClickCheckBox: () -> Unit,

    modifier: Modifier = Modifier
) {
    Log.d("체크", checked.toString())
    val animatedChecked by animateDpAsState(targetValue = if (!checked) 20.dp else 0.dp, label = "")
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(
            modifier = modifier
                .size(20.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onClickCheckBox()
                },

        ) {
            if (!checked) CheckBoxSelected(animatedChecked)
            else CheckBoxUnSelected()
        }

        Spacer(modifier = Modifier.width(7.dp))

        Text(
            text = "공개 설정",

            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = Color.Gray
            ),
        )


    }
}

@Composable
fun CheckBoxSelected(animatedChecked: Dp) {
    Box(
        modifier = Modifier
            .size(animatedChecked)
            .clip(RoundedCornerShape(3.dp))
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(15.dp),
            imageVector = Icons.Default.Check,
            contentDescription = "",
            tint = LightBlackColor
        )
    }
}

@Composable
fun CheckBoxUnSelected() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.White)
            .border(
                width = 0.5.dp,
                color = LightBlackColor,
                shape = RoundedCornerShape(3.dp)
            )
    )
}