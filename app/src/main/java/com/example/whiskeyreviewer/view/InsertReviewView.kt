package com.example.whiskeyreviewer.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.whiskeyreviewer.component.wheelPicker.Picker
import com.example.whiskeyreviewer.component.wheelPicker.rememberPickerState
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.TimeFormatter
import com.example.whiskeyreviewer.view.toolBar.InsertReviewToolBarComponent
import com.example.whiskeyreviewer.view.toolBar.ToolBarItems
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import java.time.LocalDate
import java.time.LocalTime


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReviewView() {

    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    val scrollState = rememberScrollState()

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


            TimePickerComponent(
                selectDate = LocalDate.now(),
                selectTime = LocalTime.now(),
                onDateClick = { /*TODO*/ },
                onTimeClick = {})




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

                TextInputComponent(text = "", onValueChange = {}, textCount = 0)

            }

            InsertReviewToolBarComponent(writeReviewViewModel)

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
            .padding(horizontal = 10.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(10.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = MainColor
                ),
                text = "확인"
            )
            Divider(color = Color.LightGray, thickness = 1.dp)
        }

        content()
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
fun TextColorPickerComponent() {
    val colorList = listOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFFA500), // Orange
        Color(0xFFFFC0CB), // Pink
        Color(0xFF00FF00), // Green
        Color(0xFF008000), // Dark Green
        Color(0xFF20B2AA), // Light Sea Green
        Color(0xFF0000FF), // Blue
        Color(0xFFADD8E6), // Light Blue
        Color(0xFF000080), // Navy
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFFD700), // Gold
        Color(0xFF808080), // Gray
        Color(0xFFD3D3D3), // Light Gray
        Color(0xFFA9A9A9), // Dark Gray
        Color(0xFF800080), // Purple
        Color(0xFFFF00FF), // Magenta
        Color(0xFF000000), // Black
        Color(0xFFFFFFFF)  // White
    )

    PickerContainer(onClick = {  }) {
        val chunkedList = colorList.chunked(colorList.size / 2)

        Column(modifier = Modifier.fillMaxWidth().padding(top=5.dp,bottom=5.dp)) {
            chunkedList.forEach { row ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(row) { color ->
                        SingleTextColorPickerComponent(color = color)
                    }
                }
            }
        }
    }
}



@Composable
fun SingleTextColorPickerComponent(
    color: Color = Color.Black,
    select: Boolean = false
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color)
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
                        shape = RoundedCornerShape(6.dp)
                    )
                }
            )
            .size(50.dp)
    ) {
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


    WhiskeyReviewerTheme {
        TextColorPickerComponent(

        )
    }
}


@Preview(showBackground = true)
@Composable
fun WheelPreview() {


    WhiskeyReviewerTheme {
        TextSizeWheelPickerComponent(

        )
    }
}

