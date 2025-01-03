package com.example.whiskeyreviewer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.customIcon.TagComponent
import com.example.whiskeyreviewer.component.customIcon.WhiskeyScoreComponent
import com.example.whiskeyreviewer.component.myReview.ReviewImageLazyRowComponent
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun ReviewDetailView(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel,
    mainViewModel: MainViewModel
){

    val scrollState= rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        CustomAppBarComponent(
            titleTextValue = "리뷰",
            leftButton = {

                CustomIconComponent(
                    icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                )

            },
            rightButton = {
                Spacer(modifier = Modifier.size(35.dp))
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

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
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "삭제",
                style = TextStyle.Default.copy(
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        ReviewImageLazyRowComponent(
            imageList = mainViewModel.selectWhiskyData.value.imageList,
            deleteImage = {

            },
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = mainViewModel.selectWhiskyData.value.content,
            style = TextStyle.Default.copy(
                color = LightBlackColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 15.dp)
                .heightIn(min = 150.dp)
        )
        Row(
            modifier = Modifier.padding(start = 10.dp,top=15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WhiskeyScoreComponent(
                score = mainViewModel.selectWhiskyData.value.score
            )

            Spacer(modifier = Modifier.width(15.dp))

            TagComponent(text = "개봉 ${mainViewModel.selectWhiskyData.value.open_date}")

            Spacer(modifier = Modifier.width(4.dp))

        }

        Text(
            text = "작성일: 2024-05-07",
            style = TextStyle.Default.copy(
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 5.dp)
        )
    }


}


@Preview
@Composable
fun ReviewDetailPreview(

){
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel:MainViewModel= hiltViewModel()
    ReviewDetailView(
        navController = rememberNavController(),
        writeReviewViewModel =writeReviewViewModel,
        mainViewModel = mainViewModel
    )
}