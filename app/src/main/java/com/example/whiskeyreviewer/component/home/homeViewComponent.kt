package com.example.whiskeyreviewer.component.home

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.RefreshProgressIndicator
import com.example.whiskeyreviewer.component.customComponent.WhiskyOptionDropDownMenuComponent
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.data.SelectWhiskyData
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WhiskyOptionItems
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.utils.WhiskyLanguageTransfer
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSettings


@Composable
fun SingleWhiskeyComponent(
    singleWhiskeyData:SingleWhiskeyData,
    reviewClick:()->Unit,
    deleteWhisky:(SingleWhiskeyData)->Unit,
    showOption:Boolean=true,
    dropDownMenuState: Boolean=false,
    toggleDropDownMenuState:()->Unit={},
    imageClick:()->Unit={},
    imageClickAllow:Boolean=false,
    modifyWhiskyData:(SingleWhiskeyData)->Unit={},


) {

    val dropDownMenuItems=listOf(WhiskyOptionItems.DeleteWhisky,WhiskyOptionItems.ModifyWhisky)
    var extendedState by remember { mutableStateOf( false ) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }



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

            if(showOption){
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp, top = 8.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    horizontalArrangement = Arrangement.End
                ){


                    WhiskyOptionDropDownMenuComponent(
                        modifier = Modifier,
                        toggleDropDownMenuOption = { toggleDropDownMenuState() },
                        dropDownMenuState = dropDownMenuState,
                        menuItems = dropDownMenuItems,
                        onClick = {
                            when(it){
                                WhiskyOptionItems.DeleteWhisky->{deleteWhisky(singleWhiskeyData)}
                                WhiskyOptionItems.ModifyWhisky -> {modifyWhiskyData(singleWhiskeyData)}
                            }
                        }
                    )
                }
            }


            GlideImage(
                imageModel = singleWhiskeyData.image?.image ?: R.drawable.empty_image_icon,

                modifier = Modifier
                    .size(200.dp)
                    .then(
                        if (imageClickAllow) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                imageClick()
                            }
                        } else {
                            Modifier
                        }
                    ),

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
            val name=singleWhiskeyData.korea_name?:singleWhiskeyData.english_name

            Text(
                text = name,
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 15.dp,end=10.dp)
            )





            Spacer(modifier = Modifier.height(3.dp))

            Row(
                modifier = Modifier
                    .padding(start = 17.dp)
                    .width(80.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = singleWhiskeyData.strength.toString() + "%",
                    style = TextStyle.Default.copy(
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),

                )

                IconButton(
                    onClick = { extendedState = !extendedState },
                    modifier = Modifier.size(25.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        null,
                        modifier= Modifier
                            .rotate(if (extendedState) 180f else 0f)
                            .padding(0.dp)
                        ,
                        tint = LightBlackColor
                    )
                }

            }




            //todo 메모를 보여줘야함
            if(extendedState){
                Text(
                    text = singleWhiskeyData.memo,
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 15.dp,end=15.dp)

                )

            }


            Spacer(modifier = Modifier.height(4.dp))

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
                        singleWhiskeyData.open_date?.let{
                            TagComponent(text = "개봉 + " + TimeFormatter.stringTimeFormatter(it))
                        }

                    }
                    item{
                        singleWhiskeyData.bottled_year?.let{
                            TagComponent(text=it.toString()+"년")
                        }

                    }
                    item{
                        //영어로 송수신해서 번역해야함
                        singleWhiskeyData.category?.let {category->
                            WhiskyLanguageTransfer.getKoreanTitle(category)
                                ?.let { name-> TagComponent(text=name) }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWhiskyComponent(
    mainViewModel:MainViewModel,
    myReviewItems:List<SingleWhiskeyData>,
    setSelectReview:(SingleWhiskeyData)->Unit,
    toggleConfirmDialogState:(SingleWhiskeyData)->Unit,
    dropDownMenuState: List<Boolean> = listOf() ,
    toggleDropDownMenuState:(Int)->Unit={},
    showOption: Boolean=true
) {
//    val testData= listOf(
//        SingleWhiskeyData(
//            whisky_name="잭 다니엘 10년",
//            strength = 20.0,
//            score=4.5,
//            dday=6,
//            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000039"),
//
//        ), SingleWhiskeyData(
//            whisky_name="글렌 리뱃 12년산",
//            strength = 18.5,
//            score=3.5,
//            dday=3,
//            picture = Uri.parse("content://media/picker/0/com.android.providers.media.photopicker/media/1000000037"),
//
//        ),
//        SingleWhiskeyData(
//        )
//    )
    val refreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val customScrollbarSettings = ScrollbarSettings(
        thumbUnselectedColor = MainColor,

        thumbThickness = 6.dp,
        thumbMinLength = 0.1f,
        thumbMaxLength = 0.7f
    )
    LazyColumnScrollbar(
        state = listState,
        settings = customScrollbarSettings,

        ) {

            PullToRefreshBox(
                isRefreshing = mainViewModel.whiskyListRefreshState.value,
                onRefresh = {


                    mainViewModel.getMyWhiskeyData(refresh = true)

                },
                modifier = Modifier,
                indicator = {
//                    Indicator(
//                        modifier = Modifier.align(Alignment.TopCenter).size(50.dp),
//                        isRefreshing = mainViewModel.whiskyListRefreshState.value,
//                        state = refreshState,
//                        color= LightBlackColor,
////                        containerColor = Color.White
//                    )
                    RefreshProgressIndicator(
                        isRefreshing = mainViewModel.whiskyListRefreshState.value,
                        state=refreshState,
                        strokeWidth = 4.dp,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                },
                state=refreshState
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(top = 3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item{
                        MyWhiskyCustomFilterRow(mainViewModel = mainViewModel)
                    }

                    itemsIndexed(items = myReviewItems) { index, singleWhiskeyData ->
                        SingleWhiskeyComponent(
                            singleWhiskeyData = singleWhiskeyData,
                            reviewClick = { setSelectReview(singleWhiskeyData) },
                            showOption = showOption,
                            deleteWhisky = { toggleConfirmDialogState(singleWhiskeyData) },
                            dropDownMenuState = if (showOption) dropDownMenuState[index] else false,
                            toggleDropDownMenuState = {
                                if (showOption) toggleDropDownMenuState(
                                    index
                                )
                            },
                            modifyWhiskyData = {
                                //todo 다이얼로그를 켜고 데이터를 다시 할당해야함
                                mainViewModel.modifyWhiskyMode(
                                    data = it
                                )
                            },

                            )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }


    }
}

@Composable
fun SelectWhiskyComponent(

    onSelect: () -> Unit,

    whiskeyData: WhiskyName
) {

    val animatedChecked by animateDpAsState(targetValue = if (whiskeyData.check == true) 24.dp else 0.dp, label = "")
    val interactionSource = remember { MutableInteractionSource() }
    val backgroundColor=if(whiskeyData.check == true) Color(0xFFF5F5F5) else Color.White


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
            text = whiskeyData.korea_name ?: whiskeyData.english_name,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .weight(1f)
        )
        if(whiskeyData.check == true){
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
//    WhiskeyReviewerTheme {
//        SingleWhiskeyComponent(
//            singleWhiskeyData =
//        SingleWhiskeyData(
//
//            strength = 20.0,
//            score=4.5,
//            reg_date= LocalDateTime.now().toString(),
//            image_name ="test"),
//
//        reviewClick = { /*TODO*/ }, deleteWhisky = {},showOption = true, modifyWhiskyData = {})
//    }
}
