package com.example.whiskeyreviewer.component.writeReivew

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.request.RequestOptions
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.toolBar.TextStyleItems
import com.example.whiskeyreviewer.component.toolBar.textColorList
import com.example.whiskeyreviewer.component.wheelPicker.HorizontalWheelPicker
import com.example.whiskeyreviewer.data.ImageSelectState
import com.example.whiskeyreviewer.data.UriData
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextInputComponent(
    state: RichTextState,

    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,

    placeholder: String = "내용",
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    scrollState: ScrollState,
) {
    val coroutineScope = rememberCoroutineScope()

    var prevHeight by remember { mutableStateOf(0) }




    RichTextEditor(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(300.dp)
            .padding(start = 8.dp, end = 8.dp)
            // 입력할때마다 계속 커서가 따라 가지 않음 => TextField의 height가 변하는만큼 스크롤을 움직여 주는 방식으로 해결
            .onSizeChanged {
                val diff = it.height - prevHeight
                prevHeight = it.height
                if (prevHeight == 0 || diff == 0) {
                    return@onSizeChanged
                }
                coroutineScope.launch {
                    scrollState.animateScrollTo(
                        scrollState.value + diff
                    )
                }
            },
        enabled = enabled,
        readOnly = readOnly,
        colors = RichTextEditorDefaults.richTextEditorColors(
            textColor = Color.Black,
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle.Default.copy(color = Color.Gray)
            )
        },
        isError = isError,
    )
}

@Composable
fun TextInputOptionComponent(

) {
    Column(
        Modifier
            .fillMaxWidth()


            .padding(8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(35.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.text_size_icon),
                    contentDescription = "",
                    tint = Color.Black
                )
                Text(text = "글자 크기")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier
                        .size(35.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.select_color_icon),
                    contentDescription = "",
                    tint = Color.Black
                )
                Text(text = "글자 색")

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier
                        .size(35.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.select_picture_icon),
                    contentDescription = "",
                    tint = Color.Black
                )
                Text(text = "사진 삽입")

            }
        }
    }
}

@Composable
fun TimePickerComponent(
    selectDate: LocalDate,
    onDateClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(start = 7.dp, end = 7.dp, top = 1.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MainColor)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(50.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.calender_icon),
                    contentDescription = "",
                    tint = Color.White,
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = TimeFormatter.formatDate(selectDate),
                        color = Color.White,
                        modifier = Modifier,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            color = Color.Black
                        ),
                    )
                    TextButton(
                        onClick = onDateClick,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(25.dp),

                        ) {
                        Text(
                            text = "개봉일 선택하기",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                                color = Color.Black
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PickerContainer(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 5.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White)

    ) {

        content()
    }
}

@Composable
fun TextSizePickerComponent(
    currentTextSize:Int=15,
    updateTextSize:(Int)->Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentTextSize.toString() + "pt",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 5.dp)
        )

        PickerContainer(onClick = { }) {
            HorizontalWheelPicker(
                modifier = Modifier.fillMaxWidth(),
                maxValue = 50,
                minValue = 15,
                initialSelectedItem = currentTextSize,
                onItemSelected = { selectedIndex ->
                    updateTextSize(selectedIndex)

                }
            )
        }
    }
}

@Composable
fun TextColorPickerComponent(

    updateTextColor: (Color, Int) -> Unit,

    currentSelectColorIndex: Int?=null
) {

    PickerContainer(onClick = { }) {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(textColorList) { color ->
                SingleTextColorPickerComponent(
                    select = currentSelectColorIndex == color.index,
                    color = color.color,
                    onClick = {
                        updateTextColor(color.color, color.index)
//                        updateColorIndex(index)
                    }
                )
            }
        }
    }
}

@Composable
fun SingleTextColorPickerComponent(
    color: Color = Color.Black,
    select: Boolean = false,
    onClick: (Color) -> Unit,

    ) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color)
            .clickable {
                onClick(color)
            }
            .then(
                if (select) {
                    Modifier.border(
                        width = 3.dp,
                        color = MainColor,
                        shape = RoundedCornerShape(6.dp)
                    )
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                }
            )
            .size(32.dp)
    ) {
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextStyleController(
    modifier: Modifier = Modifier,
    state: RichTextState,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTextSizeClick: (TextStyleItems) -> Unit,
    onTextColorClick: (TextStyleItems) -> Unit,
    onTextBackGroundColor:(TextStyleItems)->Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onExportClick: () -> Unit,
    writeReviewViewModel: WriteReviewViewModel
) {


    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.bold,
                onChangeClick = { writeReviewViewModel.toggleBold() },
                onClick = onBoldClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatBold,
                    contentDescription = "굵기",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.italic,
                onChangeClick = { writeReviewViewModel.toggleItalic() },
                onClick = onItalicClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatItalic,
                    contentDescription = "기울기",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.underline,
                onChangeClick = { writeReviewViewModel.toggleUnderline() },
                onClick = onUnderlineClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatUnderlined,
                    contentDescription = "밑줄",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textSize,
                onChangeClick = {
                    writeReviewViewModel.toggleTextSize()
                },
                onClick = { onTextSizeClick(TextStyleItems.TEXT_SIZE) }
            ) {
                Icon(
                    imageVector = Icons.Default.FormatSize,
                    contentDescription = "글씨크기",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textBackgroundColor,
                onChangeClick = {
                    writeReviewViewModel.toggleTextBackgroundColor()
                },
                onClick = { onTextBackGroundColor(TextStyleItems.TEXT_BACKGROUND_COLOR) }
            ) {
                Icon(
                    imageVector = Icons.Default.BorderColor,
                    contentDescription = "글자배경색",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textColor,
                onChangeClick = {
                    writeReviewViewModel.toggleTextColor()

                },
                onClick = { onTextColorClick(TextStyleItems.TEXT_COLOR) }
            ) {
                Icon(
                    imageVector = Icons.Default.FormatColorText,
                    contentDescription = "글씨색",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textStartAlign,
                onChangeClick = { writeReviewViewModel.toggleStartAlign() },
                onClick = onStartAlignClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatAlignLeft,
                    contentDescription = "시작정렬",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textMidAlign,
                onChangeClick = { writeReviewViewModel.toggleMidAlign() },
                onClick = onCenterAlignClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatAlignCenter,
                    contentDescription = "중간정렬",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            ControlWrapper(
                selected = writeReviewViewModel.textStyleState.value.textEndAlign,
                onChangeClick = { writeReviewViewModel.toggleEndAlign() },
                onClick = onEndAlignClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
                    contentDescription = "끝정렬",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MainColor,
    unselectedColor: Color = Color.LightGray,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp)
            .size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun ImageLazyRowComponent(
    imageList: List<UriData>,
    deleteImageUri: (Int) -> Unit,
    onImageAddButtonClick:()->Unit,
    currentState: Boolean,

    ) {
    val scrollState = rememberLazyListState()

    Log.d("실행", imageList.toString())
    if(currentState){
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = scrollState,
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(5.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                itemsIndexed(imageList) { index, image ->
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        GlideImage(
                            imageModel = image.uri,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            requestOptions = { RequestOptions().encodeQuality(80).override(1080,1920) }
                        )

                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 5.dp, end = 5.dp)
                                .clickable {
                                    deleteImageUri(index)
                                }
                        )
                    }
                }

                item{
                    Box(
                        modifier = Modifier
                            .size(152.dp)

                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(
                                    0.8.dp,
                                    Color.LightGray
                                ),
                                RoundedCornerShape(8.dp)
                            )
                            .background(Color.White)
                            .clickable {
                                onImageAddButtonClick()
                            }
                        ,

                        ){
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier

                                .size(100.dp)
                                .align(Alignment.Center)
                        )

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(end = 8.dp, top = 8.dp)
                                .size(40.dp)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }

            Text(
                text = "${imageList.size}/3",
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(end=10.dp,top=4.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateBottomSheet(
    onDismiss: () -> Unit,
    updateSelectData: (LocalDate)->Unit,

    ) {
    val modalBottomSheetState = rememberModalBottomSheetState()



    ModalBottomSheet(
        containerColor = Color.White,
        contentColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        CustomDatePicker(
            select = { onDismiss() },
            updateSelectData = updateSelectData
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    select:()->Unit,
    updateSelectData: (LocalDate) -> Unit
) {
    val dateState = rememberDatePickerState()

    LaunchedEffect(dateState.selectedDateMillis) {
        dateState.selectedDateMillis?.let {
            val selectedDateTime = Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            updateSelectData(selectedDateTime)
        }
    }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = MainColor,
        )
    ) {
        DatePicker(
            state = dateState,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                        text = "개봉일 선택",
                        fontSize = 20.sp
                    )
                    Button(
                        onClick = { select() },
                        modifier = Modifier.padding(end = 10.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MainColor,
//                            contentColor = Color.White,
//                            disabledContainerColor = MainColor,
//                            disabledContentColor = Color.White,
//                        )

                    )
                    {
                        Text(text = "확인")
                    }
                }
            },
            headline = {
                Text(
                    modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
                    text = "개봉일을 선택해 주세요.",
                    fontSize = 15.sp
                )
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WheelPreview1() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    WhiskeyReviewerTheme {
        TextColorPickerComponent(
            updateTextColor = { color, i ->

            },
            currentSelectColorIndex = writeReviewViewModel.textColorIndex.value
        )

    }
}

val testUris = listOf(
    Uri.parse("content://media/external/images/media/1"),
    Uri.parse("content://media/external/images/media/2"),
)

@Preview(showBackground = true)
@Composable
fun WheelPreview() {


    WhiskeyReviewerTheme {
//        ImageLazyRowComponent(
//            imageList = testUris,
//            deleteImage = {},
//            currentState = true,
//            onImageAddButtonClick = {}
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextOptionPreview() {

    val state = rememberRichTextState()
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    WhiskeyReviewerTheme {
        TextStyleController(
            state = state,
            onBoldClick = { /*TODO*/ },
            onItalicClick = { /*TODO*/ },
            onUnderlineClick = { /*TODO*/ },

            onTextSizeClick = { /*TODO*/ },
            onTextColorClick = { /*TODO*/ },
            onStartAlignClick = { /*TODO*/ },
            onEndAlignClick = { /*TODO*/ },
            onCenterAlignClick = { /*TODO*/ },
            onTextBackGroundColor = {

            },
            writeReviewViewModel = writeReviewViewModel,
            onExportClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun TextSizePickerPreview() {

    val state = rememberRichTextState()
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    WhiskeyReviewerTheme {
        TimePickerComponent(selectDate = LocalDate.now(), onDateClick = { /*TODO*/ })


    }
}