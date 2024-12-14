package com.example.whiskeyreviewer.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InsertReviewView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController
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
                    .padding(end = 13.dp, top = 15.dp,start=15.dp),
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
    val mainNavController = rememberNavController()
    WhiskeyReviewerTheme {
        InsertReviewView(writeReviewViewModel, mainNavController)
    }
}


