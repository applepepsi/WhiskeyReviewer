package com.example.whiskeyreviewer.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customComponent.PrivateCheckboxComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.myReview.RatingScoreDialog
import com.example.whiskeyreviewer.component.myReview.RatingStarComponent
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.component.toolBar.InsertReviewToolBarComponent
import com.example.whiskeyreviewer.component.writeReivew.ImageLazyRowComponent
import com.example.whiskeyreviewer.component.writeReivew.RichTextInputComponent
import com.example.whiskeyreviewer.component.writeReivew.SelectDateBottomSheet
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InsertReviewView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel,

) {


    val richTextEditorState = rememberRichTextState()
    val scrollState = rememberScrollState()



    val currentRichTextState=richTextEditorState.currentSpanStyle

    LaunchedEffect(currentRichTextState){
        writeReviewViewModel.updateSpanStyle(currentRichTextState)
    }

    if (writeReviewViewModel.selectDateBottomSheetState.value) {
        SelectDateBottomSheet(
            onDismiss = { writeReviewViewModel.toggleDateSelectBottomSheetState() },
            updateSelectData = { writeReviewViewModel.updateSelectDate(it) }
        )
    }

    RatingScoreDialog(
        toggleOption = { writeReviewViewModel.toggleRatingScoreDialogState() },
        scoreChange = { writeReviewViewModel.updateScore(it) },
        currentState = writeReviewViewModel.scoreDialogState.value,
        currentScore=writeReviewViewModel.score.value
    )

    LaunchedEffect(Unit) {
        mainViewModel.toggleSelectWhiskyState()
        if(mainViewModel.selectWhiskyDialogState.value){
            mainViewModel.toggleWhiskySelectDialogState()
        }
        if(mainViewModel.selectCustomWhiskyDialogState.value){
            mainViewModel.toggleCustomWhiskySelectDialogState()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()) {

        CustomAppBarComponent(
            titleTextValue = "리뷰 작성",
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
                CustomIconComponent(
                    icon = ImageVector.vectorResource(R.drawable.write_complete_button),
                    onClick = {
                        writeReviewViewModel.exportReview(richTextEditorState)
                    },
                    modifier=Modifier
                )
            },
        )


        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            ImageLazyRowComponent(
                imageList = writeReviewViewModel.selectedImageUri.value,
                deleteImage = {
                    writeReviewViewModel.deleteImage(it)
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = mainViewModel.currentMyReviewBottleNum.value.toString()+"병",
                    color = Color.Gray,
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    ),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = mainViewModel.writeReviewWhiskyInfo.value!!.whisky_name,
                    color = Color.Gray,
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 13.dp, top = 5.dp, start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingStarComponent(
                    score=writeReviewViewModel.score.value,
                    option=writeReviewViewModel.scoreDialogState.value,
                    toggleOption = { writeReviewViewModel.toggleRatingScoreDialogState() }
                )

                PrivateCheckboxComponent(
                    checked = writeReviewViewModel.writeReviewDate.value.is_anonymous,
                    onClickCheckBox = { writeReviewViewModel.togglePrivateState() })
            }

            RichTextInputComponent(
                state = richTextEditorState,

                scrollState=scrollState
            )


        }
//        InsertTagComponent(
//            text = writeReviewViewModel.currentTag.value,
//            onValueChange = {
//                writeReviewViewModel.updateCurrentTag(it)
//            },
//            tagList = writeReviewViewModel.tagList.value
//        )

        InsertReviewToolBarComponent(writeReviewViewModel, richTextEditorState = richTextEditorState)
    }
}


@Preview(showBackground = true)
@Composable
fun InsertReviewPreview() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel:MainViewModel= hiltViewModel()
    val mainNavController = rememberNavController()
    WhiskeyReviewerTheme {
        InsertReviewView(writeReviewViewModel, mainNavController, mainViewModel)
    }
}


