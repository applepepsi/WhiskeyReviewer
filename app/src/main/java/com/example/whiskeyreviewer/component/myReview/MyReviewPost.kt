package com.example.whiskeyreviewer.component.myReview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
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
import com.example.whiskeyreviewer.data.WhiskyDrinkingType
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDate

@Composable
fun MyReviewPost(

) {

    val testDataList= listOf(
        WhiskyReviewData(
            reviewText="스트레잇으로 마실 때는 진한 풍미가 느껴지고, 얼음을 넣어 언더락으로 즐기면 부드러움이 느껴집니다.",
            reviewStyle = "",
            private = false,
            openDate = LocalDate.of(2024, 11, 15),
            score=1.5,
            imageList = emptyList()
        ),
        WhiskyReviewData(
            reviewText="스트레잇으로 마실 때는 진한 풍미가 느껴지고, 얼음을 넣어 언더락으로 즐기면 부드러움이 느껴집니다.",
            reviewStyle = "",
            private = true,
            openDate = LocalDate.of(2024, 11, 10),
            score=1.5,
            imageList = emptyList()
        ),
        WhiskyReviewData(
            reviewText="스트레잇으로 마실 때는 진한 풍미가 느껴지고, 얼음을 넣어 언더락으로 즐기면 부드러움이 느껴집니다.",
            reviewStyle = "",
            private = false,
            openDate = LocalDate.of(2024, 11, 20),
            score=3.5,
            imageList = emptyList()
        ),
        WhiskyReviewData(
            reviewText="스트레잇으로 마실 때는 진한 풍미가 느껴지고, 얼음을 넣어 언더락으로 즐기면 부드러움이 느껴집니다.",
            reviewStyle = "",
            private = true,
            openDate = LocalDate.of(2024, 11, 1),
            score=2.5,
            imageList = emptyList()
        ),
    )

    LazyColumn(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items=testDataList){singleReview->
            MyReviewSinglePost(
                reviewText = singleReview.reviewText,
                score=singleReview.score,
                drinkingType = WhiskyDrinkingType.Highball,
                private = singleReview.private,
                openDate = TimeFormatter.dateCalculation(singleReview.openDate),
                imageList = singleReview.imageList
            )
        }
    }
}

@Composable
fun MyReviewSinglePost(
    reviewText:String="",
    score:Double=0.0,
    drinkingType: WhiskyDrinkingType,
    private:Boolean=true,

    openDate:String="",
    imageList:List<String> = emptyList()
) {




    Column(
        modifier= Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(8.dp))
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End
        ){
            //리치텍스트로 수정예정
            Text(
                text = "수정",
                style = TextStyle.Default.copy(
                    color = Color.LightGray,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "삭제",
                style = TextStyle.Default.copy(
                    color = Color.LightGray,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        ReviewImageLazyRowComponent(
            imageList = imageList,
            deleteImage = {

            },
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = reviewText,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
        )
        Row(
            modifier = Modifier.padding(start = 1.dp,top=15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WhiskeyScoreComponent(
                score = score
            )

            Spacer(modifier = Modifier.width(15.dp))

            TagComponent(text = "개봉 $openDate")

            Spacer(modifier = Modifier.width(4.dp))

            TagComponent(text = drinkingType.type)
        }
    }
}

@Composable
fun ReviewImageLazyRowComponent(
    imageList:List<String>,
    deleteImage:(Int)->Unit,
) {
    val scrollState = rememberLazyListState()


    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = scrollState,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        itemsIndexed(imageList) { index,image ->
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.TopEnd
            ) {
                GlideImage(
                    imageModel = image,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(top = 5.dp, end = 5.dp)
                        .clickable {
                            deleteImage(index)
                        }
                )
            }
        }
    }
}

@Composable
fun StarRatingComponent(

) {

}

@Composable
fun SingleStar(

) {

}

@Preview(showBackground = true)
@Composable
fun MyReviewPostPreview() {


    WhiskeyReviewerTheme {
        MyReviewSinglePost(
            reviewText="스트레잇으로 마실 때는 진한 풍미가 느껴지고, 얼음을 넣어 언더락으로 즐기면 부드러움이 느껴집니다.",
            score=3.5,
            drinkingType = WhiskyDrinkingType.Highball
        )
    }
}
