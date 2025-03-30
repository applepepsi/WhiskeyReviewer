package com.example.whiskeyreviewer.component.myReview

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.LazyPagingItems
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomTrailingIcon

import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.component.home.WhiskyCustomFilterRow
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.LightOrangeColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.WhiskyLanguageTransfer
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.skydoves.landscapist.glide.GlideImage
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSettings



//@Composable
//fun OtherUserReviewPostComponent(
//    mainViewModel:MainViewModel,
//    reviewDataList: LazyPagingItems<WhiskyReviewData>,
//    singleReviewClick:(WhiskyReviewData)->Unit,
//    modifyAllow: Boolean=true,
//    onImageSelect: (ByteArray) -> Unit={},
//    deleteReview:(WhiskyReviewData)->Unit={},
//    modifyReview:(WhiskyReviewData)->Unit={},
//    onLikeClick:(WhiskyReviewData)->Unit={},
//    getImageList: (WhiskyReviewData) -> Unit={}
//) {
//
//    val listState = rememberLazyListState()
//    val customScrollbarSettings = ScrollbarSettings(
//        thumbUnselectedColor = MainColor,
//
//        thumbThickness = 6.dp,
//        thumbMinLength = 0.1f,
//        thumbMaxLength = 0.7f
//    )
//    LazyColumnScrollbar(
//        state = listState,
//        settings = customScrollbarSettings,
//
//        ) {
//        LazyColumn(
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//                .padding(bottom = 5.dp, top = 2.dp),
//            state=listState,
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//            item{
//                WhiskyCustomFilterRow(mainViewModel = mainViewModel)
//            }
//
//            Log.d("카운트", reviewDataList.itemCount.toString())
//            items(reviewDataList.itemCount){
//                OtherUserReviewPost(
//                    singleReview = reviewDataList[it] ?: WhiskyReviewData(),
//                    singleReviewClick = {  },
//                    modifyAllow = modifyAllow,
//                    onImageSelect = {
//                        onImageSelect(it)
//                    },
//                    deleteReview = {
//                        deleteReview(it)
//                    },
//                    modifyReview = {
//                        modifyReview(it)
//                    },
//                    onLikeClick = {
//                        onLikeClick(it)
//                    },
//                    getImageList={
//                        getImageList(it)
//                    }
//                )
//            }
//
//        }
//    }
//}


@Composable
fun MyWhiskyDetailInfoComponent(
    selectWhiskyData: SingleWhiskeyData
    ) {



    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(1.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 0.8.dp,
            color = Color.LightGray
        )

        DetailInfoTextComponent(name="위스키 이름", value = selectWhiskyData.korea_name ?: "")
        DetailInfoTextComponent(name="영문 이름", value = selectWhiskyData.english_name)
        DetailInfoTextComponent(name="메모", value = selectWhiskyData.memo)
        DetailInfoTextComponent(name="종류", value = selectWhiskyData.category?.let{WhiskyLanguageTransfer.getKoreanTitle(it)}?:"")
        DetailInfoTextComponent(name="국가", value = selectWhiskyData.country)
        DetailInfoTextComponent(name="캐스크 타입", value = selectWhiskyData.cask_type)
        DetailInfoTextComponent(name="도수", value = selectWhiskyData.strength.toString()+ " %")
        DetailInfoTextComponent(name="병입 년도", value = selectWhiskyData.bottled_year?.toString()?.let { "$it 년" } ?: "")
        DetailInfoTextComponent(name="개봉일", value = selectWhiskyData.open_date?.let { it } ?: "")

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 0.8.dp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun DetailInfoTextComponent(
    name:String,
    value:String
) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier,
                    text = name,
                    style = TextStyle.Default.copy(
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(start=40.dp),
                    text = value,
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

}

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun MySingleReviewComponent(
    reviewDataList: List<WhiskyReviewData>,
    singleReviewClick: (WhiskyReviewData) -> Unit,

    onImageSelect: (ByteArray) -> Unit = {},
    deleteReview: (WhiskyReviewData) -> Unit = {},
    modifyReview: (WhiskyReviewData) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val customScrollbarSettings = ScrollbarSettings(
        thumbUnselectedColor = MainColor,
        thumbThickness = 6.dp,
        thumbMinLength = 0.1f,
        thumbMaxLength = 0.7f
    )

    LazyColumnScrollbar(
        state = listState,
        settings = customScrollbarSettings
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 5.dp, top = 2.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = reviewDataList) { singleReview ->
                val richTextState = rememberRichTextState()
                LaunchedEffect(Unit) {
                    richTextState.setHtml(singleReview.content)
                }

                Column(
                    modifier = Modifier
                        .clickable { singleReviewClick(singleReview) }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp))
                        .padding(horizontal = 15.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "수정",
                            style = TextStyle.Default.copy(
                                color = Color.LightGray,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .clickable { modifyReview(singleReview) }
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "제거",
                            style = TextStyle.Default.copy(
                                color = Color.LightGray,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .clickable { deleteReview(singleReview) }
                        )
                    }

                    if (singleReview.imageList != null) {
                        ReviewImageLazyRowComponent(
                            imageList = singleReview.imageList,
                            deleteImage = {},
                            deleteImageAllow = false,
                            onImageSelect = {
                                onImageSelect(it)
                            }
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                    }

                    RichText(
                        state = richTextState,
                        modifier = Modifier
                            .padding(start = 7.dp, end = 7.dp, top = 3.dp)
                    )

                    Row(
                        modifier = Modifier.padding(start = 1.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WhiskeyScoreComponent(
                            score = singleReview.score
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            item {
                                TagComponent(text = "개봉 ${singleReview.open_date}")
                            }
                            items(items = singleReview.tags) { singleTag ->
                                TagComponent(text = singleTag)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun OtherUserReviewPostComponent(
    mainViewModel: MainViewModel,
    reviewDataList: LazyPagingItems<WhiskyReviewData>,
    singleReviewClick: (WhiskyReviewData) -> Unit,

    onImageSelect: (ByteArray) -> Unit = {},

    onLikeClick: (WhiskyReviewData) -> Unit = {},
    getImageList: (WhiskyReviewData) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val customScrollbarSettings = ScrollbarSettings(
        thumbUnselectedColor = MainColor,
        thumbThickness = 6.dp,
        thumbMinLength = 0.1f,
        thumbMaxLength = 0.7f
    )

    LazyColumnScrollbar(
        state = listState,
        settings = customScrollbarSettings
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 5.dp, top = 2.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                WhiskyCustomFilterRow(mainViewModel = mainViewModel)
            }

            Log.d("카운트", reviewDataList.itemCount.toString())
            items(reviewDataList.itemCount) { index ->
                val singleReview = reviewDataList[index] ?: WhiskyReviewData()
                Log.d("이미지 확인", singleReview.image_names.toString())
                val richTextState = rememberRichTextState()

                val imageStateText = if (!singleReview.expendedState) "이미지 숨기기" else "이미지 표시"

                LaunchedEffect(Unit) {
                    richTextState.setHtml(singleReview.content)
                }

                Column(
                    modifier = Modifier
                        .clickable { singleReviewClick(singleReview) }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp))
                        .padding(horizontal = 15.dp, vertical = 12.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 9.dp),
                        horizontalArrangement = if (singleReview.image_names != null) Arrangement.SpaceBetween else Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (singleReview.image_names != null) {
                            Row(
                                Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = imageStateText,
                                    style = TextStyle.Default.copy(
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(end = 3.dp)
                                )

                                IconButton(
                                    onClick = {
                                        getImageList(singleReview)
                                    },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.ArrowDropDown,
                                        null,
                                        modifier = Modifier
                                            .rotate(if (singleReview.expendedState) 180f else 0f)
                                            .padding(0.dp),
                                        tint = LightBlackColor
                                    )
                                }
                            }
                        }
                        Row(
                            Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = (singleReview.like_count ?: 0).toString(),
                                style = TextStyle.Default.copy(
                                    color = Color.Gray,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(end = 3.dp)
                            )
                            IconButton(
                                onClick = {
                                    onLikeClick(singleReview)
                                },
                                modifier = Modifier.size(25.dp)
                            ) {
                                if (singleReview.like_state) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = Color.Red,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (singleReview.imageList != null && singleReview.expendedState) {
                        Log.d("이미지 결과", singleReview.imageList.toString())
                        ReviewImageLazyRowComponent(
                            imageList = singleReview.imageList,
                            deleteImage = {},
                            deleteImageAllow = false,
                            onImageSelect = {
                                onImageSelect(it)
                            }
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                    }

                    RichText(
                        state = richTextState,
                        modifier = Modifier
                            .padding(start = 7.dp, end = 7.dp, top = 3.dp)
                    )

                    Row(
                        modifier = Modifier.padding(start = 1.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WhiskeyScoreComponent(
                            score = singleReview.score
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            item {
                                TagComponent(text = "개봉 ${singleReview.open_date}")
                            }
                            items(items = singleReview.tags) { singleTag ->
                                TagComponent(text = singleTag)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ReviewImageLazyRowComponent(
    imageList:List<ByteArray>,
    deleteImage:(Int)->Unit,
    deleteImageAllow:Boolean=true,
    onImageSelect:(ByteArray)->Unit={},
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
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.TopEnd
            ) {
//                GlideImage(
//                    imageModel = image,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(RoundedCornerShape(8.dp))
//                )

                GlideImage(
                    imageModel = image,


                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onImageSelect(image)
                        },
                    placeHolder = painterResource(id = R.drawable.empty_image_icon),
                    error = painterResource(id = R.drawable.empty_image_icon)

                )

                }
                if(deleteImageAllow){
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
fun RatingStarComponent(
    score: Double=1.0,
    option:Boolean=false,
    toggleOption:()->Unit,
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Text(
            text = "점수",
            style = TextStyle.Default.copy(
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
        )
        Icon(
            modifier = Modifier
                .size(25.dp),
            imageVector = Icons.Default.Star,
            contentDescription = "",
            tint = LightOrangeColor,
        )

        Row(
            modifier = Modifier.clickable { toggleOption() }
        ){
            Text(
                text = score.toString(),
                style = TextStyle.Default.copy(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(top=2.dp)
            )
            CustomTrailingIcon(expanded = option, size = 14.dp, tint = LightBlackColor)
        }
    }
}


@Composable
fun RatingScoreDialog(
    toggleOption: () -> Unit,
    scoreChange: (Double) -> Unit,
    currentState: Boolean = true,
    currentScore: Double
) {

    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Surface(
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),

                ) {
                    Text(
                        text = "점수 입력",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                    )


                    Text(
                        text = "점수를 선택해 주세요.",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp, top = 2.dp)

                            .clickable {
                                toggleOption()
                            }
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (index in 0 until 5) {
                            SingleStar(
                                starIndex = index,
                                score = currentScore,
                                onRatingChanged = { newScore ->
                                    scoreChange(newScore)

                                }
                            )
                        }
                    }



                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text(
                            text = "확인",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier

                                .clickable {
                                    toggleOption()
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleStar(
    starIndex: Int,
    score: Double,
    onRatingChanged: (Double) -> Unit
) {

    val filled = score>starIndex

    Log.d("인덱스",starIndex.toString())
    val starImage = when {
//        halfFilled -> Icons.AutoMirrored.Filled.StarHalf
        filled -> Icons.Default.Star

        else -> Icons.Default.StarOutline
    }

    Icon(
        imageVector = starImage,
        contentDescription = null,
        modifier = Modifier
            .padding(5.dp)
            .size(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onRatingChanged(
                    ((starIndex + 1).toDouble())
                )
            },

        tint = LightOrangeColor
    )
}





@Preview(showBackground = true)
@Composable
fun MyReviewPostPreview() {


    WhiskeyReviewerTheme {

    }
}


@Preview(showBackground = true)
@Composable
fun ScoreRatingPreview() {


    WhiskeyReviewerTheme {
        RatingStarComponent(
            toggleOption = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreRatingPopupPreview() {


    WhiskeyReviewerTheme {
        RatingScoreDialog(
            toggleOption = {},
            scoreChange = {},
            currentScore = 0.0
        )
    }
}