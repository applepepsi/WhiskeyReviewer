package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.whiskeyreviewer.component.home.ConfirmDialog
import com.example.whiskeyreviewer.component.home.ImageViewerDialog
import com.example.whiskeyreviewer.component.myReview.ReviewImageLazyRowComponent
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
fun ReviewDetailView(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel,
    mainViewModel: MainViewModel
){

    val scrollState= rememberScrollState()
    val richTextState = rememberRichTextState()

    LaunchedEffect(Unit) {
        richTextState.setHtml(mainViewModel.selectWhiskyReviewData.value.content)
        Log.d("텍스트", richTextState.toText())
    }

    ConfirmDialog(
        title = "리뷰 제거",
        text = "리뷰를 제거하시겠습니까?",
        confirm = { /*TODO*/ },
        toggleOption = { mainViewModel.toggleConfirmDialog() },
        currentState = mainViewModel.confirmDialogState.value
    )

    ImageViewerDialog(
        currentImage = mainViewModel.selectImageUrl.value,
        toggleOption = { mainViewModel.toggleImageDialogState() },
        currentState = mainViewModel.imageDialogState.value
    )

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
                .padding(end = 14.dp),
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

                    .clickable {

                    }
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "제거",
                style = TextStyle.Default.copy(
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier

                    .clickable {
                        mainViewModel.toggleConfirmDialog()
                    }
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        ReviewImageLazyRowComponent(
            imageList = emptyList(),
            deleteImage = {

            },
            deleteImageAllow = false,
            onImageSelect = {
                mainViewModel.setSelectImage(it)
                mainViewModel.toggleImageDialogState()
            },
        )

        Spacer(modifier = Modifier.height(7.dp))

        RichText(
            state = richTextState,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start=10.dp,end=10.dp,top=3.dp, bottom = 5.dp)
        )

//        Text(
//            text = mainViewModel.selectWhiskyReviewData.value.content,
//            style = TextStyle.Default.copy(
//                color = LightBlackColor,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Normal
//            ),
//            modifier = Modifier
//                .padding(horizontal = 15.dp)
//                .padding(bottom = 15.dp)
//                .heightIn(min = 150.dp)
//        )
        Row(
            modifier = Modifier.padding(start = 10.dp,top=15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WhiskeyScoreComponent(
                score = mainViewModel.selectWhiskyReviewData.value.score
            )

            Spacer(modifier = Modifier.width(15.dp))

            TagComponent(text = "개봉 ${mainViewModel.selectWhiskyReviewData.value.open_date}")

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