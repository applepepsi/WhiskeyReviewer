package com.example.whiskeyreviewer.component.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import com.example.whiskeyreviewer.component.customComponent.CustomSearchBoxComponent
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SingleWhiskeyComponent(
    singleWhiskeyData:SingleWhiskeyData,
    reviewClick:()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(
                RoundedCornerShape(12.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                reviewClick()
            }
        ,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color(0xFFF6F6F6))
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            GlideImage(
//                imageModel = R.drawable.jack,
//                modifier = Modifier
//                    .size(200.dp)
//            )
            GlideImage(
                imageModel = singleWhiskeyData.picture,
                modifier = Modifier
                    .size(200.dp)
            )
            
            Spacer(modifier = Modifier.height(10.dp))
//            NavigationDrawerLabel(
//                selectColor = Color.LightGray,
//                modifier = Modifier)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier,

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = singleWhiskeyData.name,
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 15.dp)

            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = singleWhiskeyData.capacity.toString() + "ml",
                style = TextStyle.Default.copy(
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 17.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WhiskeyScoreComponent(
                    score = singleWhiskeyData.score
                )

                Spacer(modifier = Modifier.width(15.dp))

                LazyRow(

                    modifier = Modifier.padding(end=8.dp),

                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    item{
                        TagComponent(text = "개봉 D + " + singleWhiskeyData.dday.toString())
                    }
                    items(items=singleWhiskeyData.tags){singleTag->
                        TagComponent(text = singleTag)
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun MyReviewComponent(
    myReviewItems:List<SingleWhiskeyData>,
    setSelectReview:(SingleWhiskeyData)->Unit
) {
    val testData= listOf(
        SingleWhiskeyData(
            name="잭 다니엘 10년",
            capacity = 700,
            score=4.5,
            dday=6,
            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000039"),
            tags = listOf("싱글몰트","더블","트리플","더블","트리플")
        ), SingleWhiskeyData(
            name="글렌 리뱃 12년산",
            capacity = 1000,
            score=3.5,
            dday=3,
            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000037"),
            tags = listOf("싱글몰트","더블","트리플","더블","트리플")
        ),
        SingleWhiskeyData(
        )
    )

    LazyColumn(

        horizontalAlignment = Alignment.CenterHorizontally,
//            contentPadding = PaddingValues(vertical = 10.dp),
    ) {


        items(items = testData){ singleWhiskeyData->

            SingleWhiskeyComponent(
                singleWhiskeyData = singleWhiskeyData,
                reviewClick={
                    setSelectReview(singleWhiskeyData)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeComponentPreview() {


    WhiskeyReviewerTheme {
        SingleWhiskeyComponent(
            singleWhiskeyData = SingleWhiskeyData(),
            reviewClick = {}
        )
    }
}
