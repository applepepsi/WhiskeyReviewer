package com.example.whiskeyreviewer.component.customComponent

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.whiskeyreviewer.data.LiveSearchData
import com.example.whiskeyreviewer.data.OderUserReviewDropDownMenuState
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor

@Composable
fun CustomSearchBoxComponent(
    text:String="",
    onValueChange: (String) -> Unit,
    search:()->Unit,
    deleteInputText:()->Unit,
    liveSearch:Boolean= true,
    liveSearchDataList:List<LiveSearchData> = emptyList(),
    modifier:Modifier=Modifier,
    focusState:(Boolean)->Unit={},
    searchEnable:Boolean=true
) {



        Box(
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp,)
                .fillMaxWidth()

                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    BorderStroke(
                        0.5.dp,
                        Color.LightGray
                    ),
                    RoundedCornerShape(8.dp)
                )
                .height(60.dp)
            ,
            contentAlignment = Alignment.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp)
                    .onFocusChanged { focusState ->
                        focusState(focusState.isFocused)
                    },
                value = text,

                onValueChange = {
                    onValueChange(it)
                },
                placeholder = {
                    Text(
                        text = "",
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            search()
                        },
                        enabled = searchEnable
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MainColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {

                        deleteInputText()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MainColor,
                            modifier = Modifier.size(24.dp)
                        )

                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,

                    ),
                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,

                    ),
            )
            SearchBarDivider(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 50.dp)
            )
        }

//        if(liveSearch && liveSearchDataList.isNotEmpty()){
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .shadow(8.dp, RoundedCornerShape(8.dp))
//                    .background(Color.White, shape = RoundedCornerShape(8.dp))
//                    .animateContentSize()
//            ) {
//
//                    LazyColumn(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.Start
//                    ) {
//                        items(
//                            items = liveSearchDataList,
//                        ) { item ->
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                    }
//                                    .padding(12.dp)
//                            ) {
//                                Text(
//                                    text = item.whisky_korea_name!!,
//                                    style = TextStyle(fontSize = 18.sp),
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                            }
//                        }
//
//                }
//            }
//        }



}

@Composable
fun LiveSearchBoxComponent(
    text:String="",
    onValueChange: (String) -> Unit,
    search:()->Unit,
    deleteInputText:()->Unit,
    onLiveSearchDataClick:(String)->Unit,
    onLiveSearchEmptyLick:(String)->Unit,
    liveSearchDataList:List<LiveSearchData>? = null,
    liveSearchDropDownState:Boolean=false,
    toggleLiveSearchDropDownMenuState:(Boolean)->Unit={},
) {


    var searchBarSize by remember { mutableStateOf(IntSize.Zero) }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier= Modifier
            .fillMaxWidth()


    ) {
        CustomSearchBoxComponent(
            text = text,
            onValueChange = {
                onValueChange(it)

            },
            searchEnable = false,
            search = {
//                toggleLiveSearchDropDownMenuState(false)
//                if(liveSearchDataList.isNullOrEmpty()){
//                    onLiveSearchEmptyLick(text)
//                }else{
//                    onLiveSearchDataClick(text)
//                }

            },
            deleteInputText = { deleteInputText() },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    searchBarSize = coordinates.size
                },
            focusState = {state->
                isFocused=state
                if (state && text.isNotBlank()) {
                    toggleLiveSearchDropDownMenuState(true)
                } else {
                    toggleLiveSearchDropDownMenuState(false)
                }
            }

        )
        //todo 터치 시 검색, 다른뷰로 이동시 onDismissRequest, 드롭다운 디자인 변경

        if (liveSearchDataList.isNullOrEmpty()) {

            Row(Modifier.padding(start = 15.dp, top = 5.dp, end = 20.dp)) {
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = liveSearchDropDownState,
                        onDismissRequest = { toggleLiveSearchDropDownMenuState(false) },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { searchBarSize.width.toDp() })
                            .heightIn(max = 150.dp),
                        properties = PopupProperties(
                            focusable = false,
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        ),

                        ) {
                        DropdownMenuItem(
                            modifier = Modifier,
                            content = {
                                LiveSearchItem("$text 에 대해 검색 하기")
                            },
                            onClick = {
                                toggleLiveSearchDropDownMenuState(false)
                                onLiveSearchEmptyLick(text)
                            },)
                    }
                }
            }
        }else{

            Row(Modifier.padding(start = 15.dp, top = 5.dp, end = 20.dp)) {
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = liveSearchDropDownState,
                        onDismissRequest = { toggleLiveSearchDropDownMenuState(false) },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { searchBarSize.width.toDp() })

//                            .width(with(LocalDensity.current) { searchBarSize.width.toDp() - (horizontalPadding * 2) })
                            .heightIn(max = 150.dp),
                        properties = PopupProperties(
                            focusable = false,
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        ),

                        ) {

                        liveSearchDataList.forEach { item ->
                            val name = item.whisky_korea_name ?: item.whisky_english_name!!
                            DropdownMenuItem(
                                modifier = Modifier,
                                content = {
                                    LiveSearchItem(name)
                                },
                                onClick = {
                                    toggleLiveSearchDropDownMenuState(false)
                                    onLiveSearchDataClick(name)
                                },

                                )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun LiveSearchItem(
    liveSearchItem: String,

    ) {
    Log.d("liveSearchItem", liveSearchItem.toString())


    Text(
        text = liveSearchItem,
        modifier = Modifier
        ,
        style = TextStyle.Default.copy(
            color = LightBlackColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun SearchBarDivider(
    modifier: Modifier = Modifier
){
    HorizontalDivider(
        modifier = modifier
            .width(1.5.dp),
        thickness = 30.dp,
        color = MainColor
    )
}




@Preview
@Composable
fun SearchBarPreview(
    modifier: Modifier = Modifier
){
    LiveSearchBoxComponent(onValueChange = {}, search = { /*TODO*/ }, deleteInputText = {}, onLiveSearchDataClick = {},onLiveSearchEmptyLick = {

    })
}