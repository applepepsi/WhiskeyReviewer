package com.example.whiskeyreviewer.component.toolBar

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whiskeyreviewer.component.writeReivew.InsertTagComponent
import com.example.whiskeyreviewer.data.ToolBarItems
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.component.writeReivew.TextColorPickerComponent
import com.example.whiskeyreviewer.component.writeReivew.TextSizePickerComponent
import com.example.whiskeyreviewer.component.writeReivew.TextStyleController
import com.example.whiskeyreviewer.component.writeReivew.TimePickerComponent
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState


@Composable
fun InsertReviewToolBarComponent(
    writeReviewViewModel: WriteReviewViewModel,
    richTextEditorState:RichTextState
) {


//    val singleImagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            writeReviewViewModel.setSelectedImage(it)
//        }
//    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3),
        onResult = { uris ->
            writeReviewViewModel.setSelectedImage(uris)
        }
    )


    Column(
        modifier = Modifier.imePadding(),
        verticalArrangement = Arrangement.Bottom
    ) {

        val selectedItem=writeReviewViewModel.selectedItem.value
        val selectedTextStyleItem=writeReviewViewModel.selectedTextStyleItem.value

        selectedItem?.let {

            selectedTextStyleItem?.let{
                when(selectedTextStyleItem){
                    TextStyleItems.TEXT_SIZE ->{
                        TextSizePickerComponent(
                            currentTextSize = writeReviewViewModel.textSize.value,
                            updateTextSize = {
                                writeReviewViewModel.updateTextSize(it)
                                richTextEditorState.toggleSpanStyle(SpanStyle(fontSize = writeReviewViewModel.textSize.value.sp))
                            }
                        )
                    }
                    TextStyleItems.TEXT_COLOR ->{
                        TextColorPickerComponent(
                            currentSelectColorIndex=writeReviewViewModel.textColor.value.index,
                            updateTextColor= {color,index->
                                writeReviewViewModel.updateTextColor(color,index)
                                richTextEditorState.toggleSpanStyle(SpanStyle(color = writeReviewViewModel.textColor.value.color))
                        },

                        )
                    }
                    TextStyleItems.TEXT_BACKGROUND_COLOR ->{
                        TextColorPickerComponent(
                            currentSelectColorIndex=writeReviewViewModel.textBackgroundColor.value.index,
                            updateTextColor = {color,index->
                                writeReviewViewModel.updateTextBackgroundColor(color,index)
                                richTextEditorState.toggleSpanStyle(SpanStyle(background = writeReviewViewModel.textBackgroundColor.value.color))
                            },

                        )
                    }
                }
            }

            when (selectedItem) {
                is ToolBarItems.TextStyle -> {
                    TextStyleController(
                        modifier = Modifier.weight(2f),
                        state = richTextEditorState,
                        onBoldClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        },
                        onItalicClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        },
                        onUnderlineClick = {
                            richTextEditorState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                        },
                        onTextSizeClick = {
                            writeReviewViewModel.selectTextStyleItem(it)
                        },
                        onTextBackGroundColor = {
                            writeReviewViewModel.selectTextStyleItem(it)
                        },
                        onTextColorClick = {
                            writeReviewViewModel.selectTextStyleItem(it)
                        },
                        onStartAlignClick = {
                            richTextEditorState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                        },
                        onEndAlignClick = {
                            richTextEditorState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                        },
                        onCenterAlignClick = {
                            richTextEditorState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                        },
                        onExportClick = {
                            Log.d("추출", richTextEditorState.toHtml())
                        },
                        writeReviewViewModel = writeReviewViewModel
                    )

                }
                is ToolBarItems.Picture -> {

                    multiplePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    //이미지는 한번 실행되면 선택된 아이템을 초기화 할 필요가 있음
                    writeReviewViewModel.resetItem()
                    writeReviewViewModel.resetTextStyleItem()
                }
                is ToolBarItems.SelectDate -> {
                    TimePickerComponent(
                        selectDate = writeReviewViewModel.writeReviewDate.value.open_date,

                        onDateClick = { writeReviewViewModel.toggleDateSelectBottomSheetState() },
                        )
                    writeReviewViewModel.resetTextStyleItem()
                }
                is ToolBarItems.SelectTag -> {
                    InsertTagComponent(
                        text = writeReviewViewModel.currentTag.value,
                        onValueChange = {
                            writeReviewViewModel.updateCurrentTag(it)
                        },
                        tagList = writeReviewViewModel.tagList.value,
                        deleteClick = {
                            writeReviewViewModel.deleteTag(it)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(bottom = 5.dp)
                .clip(shape = RoundedCornerShape(15.dp))

        ) {

            Row(modifier = Modifier) {
                ToolBarItems.items.forEach { item ->
                    AddInsertReviewToolBarItem(item, writeReviewViewModel)
                }
            }
        }
    }
}


@Composable
fun RowScope.AddInsertReviewToolBarItem(
    item: ToolBarItems,
    writeReviewViewModel: WriteReviewViewModel,

    ) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
//    val backgroundColor = if (isPressed) Color.Red else Color.Black
//    val contentColor = if (isPressed) Color.Blue else Color.Yellow
    val contentColor = Color.White
    val backgroundColor = MainColor
    Column(
        modifier = Modifier

            .weight(1f)
            .clickable(
                onClick = { writeReviewViewModel.selectItem(item) },
//                interactionSource = interactionSource,
//                indication = rememberRipple()
            )
            .background(backgroundColor)
            .padding(vertical = 7.dp,),
        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = contentColor,
            modifier = Modifier.size(25.dp)
        )

        Text(
            text = item.title,
            style = TextStyle.Default.copy(color = contentColor, fontSize = 13.sp, fontWeight = FontWeight.Normal),
            modifier = Modifier
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    val richTextEditorState = rememberRichTextState()
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    WhiskeyReviewerTheme {
        InsertReviewToolBarComponent(writeReviewViewModel,richTextEditorState)
    }
}