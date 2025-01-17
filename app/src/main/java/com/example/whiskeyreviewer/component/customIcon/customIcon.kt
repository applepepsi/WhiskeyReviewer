package com.example.whiskeyreviewer.component.customIcon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.LightOrangeColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.OrangeColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme

@Composable
fun CustomIconComponent(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier=Modifier.size(35.dp),
    iconSize: Modifier=Modifier.size(25.dp),
    onClickAllow:Boolean=true,
    backGroundColor:Color= MainColor,
    tint:Color=Color.White
) {

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .background(backGroundColor)
            .size(35.dp)
            .then(
                if (onClickAllow) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = iconSize
                .size(25.dp),
            imageVector = icon,
            contentDescription = "",
            tint = tint,
        )
    }
}

@Composable
fun WhiskeyScoreComponent(
    score: Double
) {


    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(7.dp))
//                .border(
//                    width = 0.5.dp,
//                    color = Color.LightGray,
//                    shape = RoundedCornerShape(7.dp)
//                )

//                .background(MainColor)
                .size(25.dp),

            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier
                    .size(25.dp),
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = LightOrangeColor,
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text=score.toString(),
            style = TextStyle.Default.copy(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }

}

@Composable
fun TagComponent(
    text:String=""
) {

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .background(OrangeColor)
            .padding(start = 8.dp,end=8.dp,top=5.dp,bottom=5.dp)
            .widthIn(30.dp),

        contentAlignment = Alignment.Center
    ){
        Text(
            text=text,
            style = TextStyle.Default.copy(
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun CustomTagComponent(
    text:String="",
    deleteClick:()->Unit
){


    Box(
        modifier = Modifier
            .padding(4.dp)
    ) {

        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(7.dp))
                .background(OrangeColor)
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                .widthIn(30.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = "# $text",
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                ),

            )
        }

        Icon(
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.TopEnd)
                .offset(x = -2.dp, y = (-7).dp)
                .clickable(
                    interactionSource = remember{ MutableInteractionSource() },
                    indication = null
                ) {
                    deleteClick()
                },
            imageVector = Icons.Default.Clear,
            contentDescription = "",
            tint = LightBlackColor,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun CustomIconPreview() {


    WhiskeyReviewerTheme {
        CustomTagComponent(
            text="테스트",
            {}
        )
    }
}