package com.example.whiskeyreviewer.view

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nextclass.utils.MaxTextCount
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customIcon.WriteViewButtonComponent
import com.example.whiskeyreviewer.component.wheelPicker.HorizontalWheelPicker
import com.example.whiskeyreviewer.component.wheelPicker.Picker
import com.example.whiskeyreviewer.component.wheelPicker.rememberPickerState
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.view.toolBar.InsertReviewToolBarComponent
import com.example.whiskeyreviewer.view.toolBar.TextColors
import com.example.whiskeyreviewer.view.toolBar.TextStyleItems
import com.example.whiskeyreviewer.view.toolBar.textColorList
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults.richTextEditorColors
import com.skydoves.landscapist.glide.GlideImage
import java.net.URI
import java.time.LocalDate
import java.time.LocalTime


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReviewView() {

    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val richTextEditorState = rememberRichTextState()
    val scrollState = rememberScrollState()



    val currentRichTextState=richTextEditorState.currentSpanStyle
    LaunchedEffect(currentRichTextState){
        writeReviewViewModel.updateSpanStyle(currentRichTextState)
    }

        Column(modifier = Modifier.fillMaxSize()) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WriteViewButtonComponent(
                    icon = ImageVector.vectorResource(R.drawable.back_button_icon)
                )

                Text(
                    text = "리뷰 작성",
                    style = TextStyle.Default.copy(
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                WriteViewButtonComponent(
                    icon = ImageVector.vectorResource(R.drawable.write_complete_button)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 3.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 13.dp, top = 15.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommentCheckboxComponent(checked = true, onClickCheckBox = { /*TODO*/ })

                }



                ImageLazyRowComponent(
                    imageList = writeReviewViewModel.selectedImageUri.value,
                    deleteImage = {
                        writeReviewViewModel.deleteImage(it)
                    },
                )


                RichTextInputComponent(
                    state = richTextEditorState,
                    onValueChange = {

                    },)
            }

            InsertReviewToolBarComponent(writeReviewViewModel,richTextEditorState=richTextEditorState)
        }
}

@Composable
fun TextInputComponent(
    text: String,
    onValueChange: (String) -> Unit,
    textCount: Int
) {
    Column(

    ) {
        Box(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, top = 5.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    if (it.length <= MaxTextCount) {
                        onValueChange(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp)
                    .padding(14.dp),
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "내용",
                                style = TextStyle.Default.copy(color = Color.Gray, fontSize = 20.sp)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

//        Text(
//            text = "${textCount}/${MaxTextCount}",
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(end = 25.dp, top = 3.dp, bottom = 3.dp),
//            fontSize = 12.sp,
//            color = Color.DarkGray
//        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextInputComponent(
    state: RichTextState,
    onValueChange: (String) -> Unit,

    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,

    placeholder: String = "내용",
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {
    Column(modifier = modifier) {
        RichTextEditor(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .padding(14.dp)
                ,
            enabled = enabled,
            readOnly = readOnly,
            colors = richTextEditorColors(
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
        ){
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
                Text(text="글자 크기")
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
                Text(text="글자 색")

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
                Text(text="사진 삽입")

            }
        }
    }
}

@Composable
fun CommentCheckboxComponent(
    checked:Boolean,
    onClickCheckBox: () -> Unit,

    modifier: Modifier=Modifier
) {

    Row(
        modifier = modifier

        ,
        verticalAlignment = Alignment.CenterVertically,
    ){

        val iconImage=if(checked){
            ImageVector.vectorResource(R.drawable.custom_checkbox_on)
        }else{
            ImageVector.vectorResource(R.drawable.custom_checkbox_off)
        }

        Image(
            contentDescription = null,
            imageVector=iconImage,
            modifier = Modifier
                .size(23.dp)
                .clickable {
                    onClickCheckBox()
                }
        )

        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = "공개 설정",

            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = Color.Gray
            ),
        )



    }
}



@Composable
fun TimePickerComponent(
    selectDate: LocalDate,
    selectTime: LocalTime,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,

) {
    Box(
        modifier = Modifier
            .padding(start = 7.dp, end = 7.dp, top = 25.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MainColor)
            .height(90.dp),
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
                        .size(40.dp),
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
                        modifier = Modifier
                    )
                    TextButton(
                        onClick = onDateClick,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(25.dp)
                    ) {
                        Text(
                            text = "날짜 선택하기",
                            color = Color.White
                        )
                    }
                }
            }

            Divider(
                color = Color.White,
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )

            Row {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.schedule_icon),
                    contentDescription = "",
                    tint = Color.White,
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = TimeFormatter.formatTime(selectTime),
                        color = Color.White,
                        modifier = Modifier
                    )
                    TextButton(
                        onClick = onTimeClick,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(25.dp)
                    ) {
                        Text(
                            text = "시간 선택하기",
                            color = Color.White
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

//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.End,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                modifier = Modifier
//                    .clickable(onClick = onClick)
//                    .padding(10.dp),
//                style = TextStyle(
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Normal,
//                    fontStyle = FontStyle.Normal,
//                    color = MainColor
//                ),
//                text = "확인"
//            )
//            Divider(color = Color.LightGray, thickness = 1.dp)
//        }

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
            text=currentTextSize.toString()+"pt",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom=5.dp)
        )

        PickerContainer(onClick = {  }) {
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
fun TextSizeWheelPickerComponent() {
    val year = remember { (8..54).map { it.toString() } }
    val yearPickerState = rememberPickerState()
    val scrollState = rememberScrollState()

    PickerContainer(onClick = {  }) {
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Picker(
                    state = yearPickerState,
                    items = year,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.5f),
                    textModifier = Modifier.padding(3.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    startIndex = 2024,
                    separateWord = " pt"
                )
            }
        }
    }
}

@Composable
fun TextColorPickerComponent(

    updateTextColor: (Color,Int) -> Unit,

    currentSelectColorIndex: Int?=null
) {

    PickerContainer(onClick = {  }) {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(textColorList) { color ->
                SingleTextColorPickerComponent(
                    select = currentSelectColorIndex==color.index,
                    color = color.color,
                    onClick = {
                        updateTextColor(color.color,color.index)
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
            .fillMaxWidth()
            ,
        contentPadding = PaddingValues(start=3.dp),
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
        item{
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
    imageList:List<Uri>,
    deleteImage:(Int)->Unit,
) {
    val scrollState = rememberLazyListState()

    Log.d("실행","실행")

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

@Preview(showBackground = true)
@Composable
fun InsertReviewPreview() {


    WhiskeyReviewerTheme {
        InsertReviewView(

        )
    }
}

@Preview(showBackground = true)
@Composable
fun WheelPreview1() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    WhiskeyReviewerTheme {
        TextColorPickerComponent(
            updateTextColor = {color, i ->
                              
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
        ImageLazyRowComponent(
            imageList = testUris,
            deleteImage = {}
        )
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
            onTextBackGroundColor={

            },
            writeReviewViewModel = writeReviewViewModel,
            onExportClick = {})
    }
}
