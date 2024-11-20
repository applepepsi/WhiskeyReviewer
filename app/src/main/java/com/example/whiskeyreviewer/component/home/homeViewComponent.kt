package com.example.whiskeyreviewer.component.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun SingleWhiskeyComponent(
    singleWhiskeyData:SingleWhiskeyData
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(330.dp)
        .padding(horizontal = 20.dp)
        .border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        ),
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            imageModel = singleWhiskeyData.picture,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)

        )

        Text(
            text=singleWhiskeyData.name,
            style = TextStyle.Default.copy(
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start=15.dp)

        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text=singleWhiskeyData.capacity.toString()+"ml",
            style = TextStyle.Default.copy(
                color = Color.Gray,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 17.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(start=15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WhiskeyScoreComponent(
                score=singleWhiskeyData.score
            )

            Spacer(modifier = Modifier.width(15.dp))

            TagComponent(text="개봉 D - "+singleWhiskeyData.dday.toString())
        }

    }
}


@Preview(showBackground = true)
@Composable
fun HomeComponentPreview() {


    WhiskeyReviewerTheme {
        SingleWhiskeyComponent(
            singleWhiskeyData = SingleWhiskeyData()
        )
    }
}
