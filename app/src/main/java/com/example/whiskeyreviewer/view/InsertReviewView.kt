package com.example.whiskeyreviewer.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.MaxTextCount
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customIcon.WriteCompleteButton
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.view.toolBar.InsertReviewToolBarComponent


@Composable
fun InsertReviewView() {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {

        WriteCompleteButton()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .weight(1f)
        ) {
            TextInputComponent(text = "", onValueChange = {}, textCount = 0)

        }

        InsertReviewToolBarComponent()
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
                .padding(start = 7.dp, end = 7.dp, top = 5.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .border(1.5.dp, Color.LightGray, shape = RoundedCornerShape(15.dp))
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

        Text(
            text = "${textCount}/${MaxTextCount}",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 25.dp, top = 3.dp, bottom = 3.dp),
            fontSize = 12.sp,
            color = Color.DarkGray
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
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


@Preview(showBackground = true)
@Composable
fun InsertReviewPreview() {


    WhiskeyReviewerTheme {
        InsertReviewView(

        )
    }
}