package com.example.whiskeyreviewer.component.writeReivew

import android.annotation.SuppressLint
import android.text.TextUtils.replace
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text2.input.TextFieldState


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.component.customIcon.CustomTagComponent
import com.example.whiskeyreviewer.data.WhiskyDrinkingType
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun InsertTagComponent(
    text:String="",
    onValueChange:(String)->Unit,
    tagList:List<String> = emptyList()
) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=5.dp,end=5.dp,top=4.dp)

    ) {

        Text(
            text="태그를 추가해 주세요.",
            style = TextStyle.Default.copy(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(start=5.dp,bottom=2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(50.dp)
//                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
//                .clip(shape = RoundedCornerShape(10.dp))
                .padding(start=8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){


            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                readOnly = false,
                singleLine = true,

                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        LazyRow(
                            modifier = Modifier
                                //사이즈가 변할때마다 listState를 사용해 마지막 인덱스까지 이동하도록 했음
                                .onSizeChanged {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(
                                            index = tagList.size
                                        )
                                    }
                                },
                            state=listState
                        ){
                            items(items=tagList){singleTag->
                                CustomTagComponent(
                                    text=singleTag,
                                )
                            }

                            item{
                                if(text.isEmpty()){
//                                    Text(
//                                        text="EX. 온더락",
//                                        style = TextStyle.Default.copy(
//                                            color = Color.LightGray,
//                                            fontSize = 20.sp,
//                                            fontWeight = FontWeight.Normal
//                                        ),
//                                        modifier = Modifier.padding(start=5.dp)
//                                    )
                                }else{
                                    Box(
                                        content = { innerTextField() },
                                        modifier = Modifier

                                            .padding(end = 4.dp,top=5.dp),

                                        )
                                }

                            }
                        }
                    }
                }
            )
        }
    }

}

