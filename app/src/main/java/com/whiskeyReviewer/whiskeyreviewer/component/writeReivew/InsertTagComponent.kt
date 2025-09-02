package com.whiskeyReviewer.whiskeyreviewer.component.writeReivew

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whiskeyReviewer.whiskeyreviewer.component.customIcon.CustomTagComponent
import com.whiskeyReviewer.whiskeyreviewer.component.home.NavigationDrawerLabel

import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun InsertTagComponent(
    text:String="",
    onValueChange:(String)->Unit,
    tagList:List<String> = emptyList(),
    deleteClick:(Int)->Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 4.dp)

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
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){


            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                readOnly = false,
                singleLine = true,

                textStyle = TextStyle(
                    fontSize = 15.sp
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
                            itemsIndexed(items=tagList){index,singleTag->

                                CustomTagComponent(
                                    text=singleTag,
                                    deleteClick = {
                                        deleteClick(index)
                                    }
                                )
                            }

                            item{
                                Box(
                                    content = { innerTextField() },
                                    modifier = Modifier

                                        .padding(end = 4.dp,top=10.dp),
                                )
                            }
                        }
                    }
                }
            )
        }
        NavigationDrawerLabel(selectColor = Color.LightGray, modifier = Modifier)
    }

}

