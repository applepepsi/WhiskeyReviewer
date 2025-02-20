package com.example.whiskeyreviewer.component.customComponent

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun EmptyMyWhiskyReviewComponent(
    text:String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom=50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(150.dp),
            imageVector = ImageVector.vectorResource(R.drawable.empty_bottle),
            contentDescription = "",
            tint = LightBlackColor,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = text,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun EmptyWhiskySearchComponent(

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top=5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "해당 위스키가 존재하지 않습니다.",
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun EmptyReviewDataComponent(
    text:String,
    icon:ImageVector
) {
    Column(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(80.dp),
            imageVector = icon,
            contentDescription = "",
            tint = MainColor,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = text,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 19.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}


@Composable
@Preview
fun Preview(){
    EmptyMyWhiskyReviewComponent(text="text")
}
