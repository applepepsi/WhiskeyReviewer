package com.example.whiskeyreviewer.component.customIcon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.OrangeColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme

@Composable
fun CustomIconComponent(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier=Modifier
) {

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .background(MainColor)
            .size(35.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(23.dp),
            imageVector = icon,
            contentDescription = "",
            tint = Color.White,
        )
    }
}

@Composable
fun WhiskeyScoreComponent(score: Double) {


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
                tint = Color.Yellow,
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text=score.toString(),
            style = TextStyle.Default.copy(
                color = Color.Gray,
                fontSize = 15.sp,
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
            .padding(start = 7.dp,end=5.dp,top=5.dp,bottom=7.dp),

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


@Preview(showBackground = true)
@Composable
fun CustomIconPreview() {


    WhiskeyReviewerTheme {
        CustomIconComponent(
            icon=ImageVector.vectorResource(R.drawable.write_complete_button),
            onClick = {},
            modifier = Modifier
        )
    }
}