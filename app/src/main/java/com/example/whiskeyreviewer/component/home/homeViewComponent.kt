package com.example.whiskeyreviewer.component.home

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.data.SelectWhiskyData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.skydoves.landscapist.glide.GlideImage


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
                text = singleWhiskeyData.whisky_name,
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 15.dp)

            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = singleWhiskeyData.strength.toString() + "%",
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
                    item{
                        TagComponent(text=singleWhiskeyData.saleDate.toString()+"년")
                    }

//                    items(items=singleWhiskeyData.tags){singleTag->
//                        TagComponent(text = singleTag)
//                    }
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
            whisky_name="잭 다니엘 10년",
            strength = 20.0,
            score=4.5,
            dday=6,
            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000039"),

        ), SingleWhiskeyData(
            whisky_name="글렌 리뱃 12년산",
            strength = 18.5,
            score=3.5,
            dday=3,
            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000037"),

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

@Composable
fun SelectWhiskyComponent(

    onSelect: () -> Unit,

    whiskeyData: SelectWhiskyData
) {

    val animatedChecked by animateDpAsState(targetValue = if (whiskeyData.check) 24.dp else 0.dp, label = "")
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor=if(whiskeyData.check) Color(0xFFF5F5F5) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect()
            }
            .background(backgroundColor)
            .padding(5.dp)
            .padding(end = 12.dp)
            ,

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = whiskeyData.name,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(start = 15.dp)
        )
        if(whiskeyData.check){
            CheckBoxSelected(animatedChecked)
        }else{
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(7.dp))
                    .border(
                        BorderStroke(
                            0.8.dp,
                            Color.Gray
                        ),
                        RoundedCornerShape(7.dp)
                    )
                    .size(24.dp)

            )
        }

    }
}

@Composable
fun CheckBoxSelected(
    animatedChecked: Dp
) {
    Box(
        modifier = Modifier
            .size(animatedChecked)
            .clip(RoundedCornerShape(7.dp))
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = Icons.Default.Check,
            contentDescription = "",
            tint = LightBlackColor
        )
    }
}

@Composable
fun DialogLabel(
    selectColor: Color,
    modifier: Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()

            .background(selectColor)
            .height(0.5.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun HomeComponentPreview() {

    val whiskeyData= listOf(SelectWhiskyData())
    WhiskeyReviewerTheme {
        SelectWhiskyComponent(onSelect = { /*TODO*/ }, SelectWhiskyData())
    }
}
