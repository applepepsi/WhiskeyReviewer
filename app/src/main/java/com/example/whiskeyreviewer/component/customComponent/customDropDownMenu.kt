package com.example.whiskeyreviewer.component.customComponent

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.countryData
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.data.CountryItems
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.TapLayoutItems
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.data.WhiskyOptionItems
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenuComponent(
    category:String="",
    value: WhiskeyFilterItems,
    onValueChange: (WhiskeyFilterItems) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<WhiskeyFilterItems>,
    ) {
    val backgroundColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) MainColor else Color.White, label = ""
    )

    val selectBorder=  if (dropDownMenuOption) 1.5.dp else 0.5.dp


    val textAndIconColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) LightBlackColor else Color.LightGray, label = ""
    )

    Column {

        ExposedDropdownMenuBox(
            expanded = dropDownMenuOption,
            onExpandedChange = { toggleDropDownMenuOption() },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(
                        selectBorder,
                        Color.LightGray
                    ),
                    RoundedCornerShape(8.dp)
                )

                .background(backgroundColor)

        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 12.dp)
                    .menuAnchor(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text=category,
                    style = TextStyle.Default.copy(
                        color = textAndIconColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier

                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(value.icon),
                    contentDescription = null,
                    tint = textAndIconColor,
                    modifier = Modifier.size(15.dp)
                )
            }
            ExposedDropdownMenu(
                modifier = Modifier
                    .width(150.dp)
                    .background(Color.White),
                expanded = dropDownMenuOption,
                onDismissRequest = { toggleDropDownMenuOption() }
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item.title,
                                style = TextStyle.Default.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        onClick = {
                            onValueChange(item)
                            toggleDropDownMenuOption()
                        },
                        trailingIcon = {
                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun WhiskeyDetailDropDownMenuComponent(
    value: MyReviewFilterItems,
    onValueChange: (MyReviewFilterItems) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<MyReviewFilterItems>,
) {
    MyReviewCustomDropdownMenu(
        value = value,
        onValueChange = onValueChange,
        dropDownMenuOption = dropDownMenuOption,
        toggleDropDownMenuOption = toggleDropDownMenuOption,
        menuItems = menuItems,
        itemToString = { it.title }
    )
}


@Composable
fun WhiskeyDetailBottleNumDropDownMenuComponent(
    value: Int,
    onValueChange: (Int) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<Int>,
) {
    MyReviewCustomDropdownMenu(
        value = value,
        onValueChange = onValueChange,
        dropDownMenuOption = dropDownMenuOption,
        toggleDropDownMenuOption = toggleDropDownMenuOption,
        menuItems = menuItems,
        itemToString = { "${it} 번 병" }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MyReviewCustomDropdownMenu(
    value: T,
    onValueChange: (T) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<T>,
    itemToString: (T) -> String
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) MainColor else Color.White, label = ""
    )

    val selectBorder = if (dropDownMenuOption) 1.5.dp else 0.5.dp

    val textAndIconColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) LightBlackColor else Color.LightGray, label = ""
    )

    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(

            expanded = dropDownMenuOption,
            onExpandedChange = { toggleDropDownMenuOption() },
            modifier = Modifier

                .clip(RoundedCornerShape(8.dp))
//                .border(BorderStroke(selectBorder, Color.LightGray), RoundedCornerShape(8.dp))
                .background(backgroundColor)
        ) {
            Row(
                modifier = Modifier
//                    .width(85.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .menuAnchor(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = itemToString(value),
                    style = TextStyle.Default.copy(
                        color = textAndIconColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                )


                CustomTrailingIcon(expanded = dropDownMenuOption, size = 23.dp, tint = textAndIconColor)
            }
            ExposedDropdownMenu(

                scrollState = rememberScrollState(),
                modifier = Modifier

                    .width(150.dp)
                    .heightIn(max = 200.dp)
                    .background(Color.White),
                expanded = dropDownMenuOption,
                onDismissRequest = { toggleDropDownMenuOption() }
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemToString(item),
                                style = TextStyle.Default.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        onClick = {
                            onValueChange(item)
                            toggleDropDownMenuOption()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTrailingIcon(expanded: Boolean,size: Dp,tint:Color,modifier: Modifier=Modifier) {
    Icon(
        Icons.Filled.ArrowDropDown,
        null,
        modifier= modifier
            .rotate(if (expanded) 180f else 0f)
            .size(size)
            .padding(0.dp)
            ,
        tint = tint
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiskeyFilterDropDownMenuComponent(
    modifier: Modifier,
    value: TapLayoutItems,
    onValueChange: (TapLayoutItems) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<TapLayoutItems>,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) MainColor else Color.White, label = ""
    )

    val selectBorder=  if (dropDownMenuOption) 1.5.dp else 0.5.dp


    val textAndIconColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) LightBlackColor else Color.LightGray, label = ""
    )

    Column(
        modifier=modifier
    ) {

        ExposedDropdownMenuBox(
            expanded = dropDownMenuOption,
            onExpandedChange = { toggleDropDownMenuOption() },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(
                        selectBorder,
                        Color.LightGray
                    ),
                    RoundedCornerShape(8.dp)
                )
                .widthIn(max=140.dp)
                .height(42.dp)
                .background(backgroundColor)

        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 12.dp)
                    .menuAnchor(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text=value.title ?:"test",
                    style = TextStyle.Default.copy(
                        color = textAndIconColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)

                )

                Spacer(modifier = Modifier.width(5.dp))


                CustomTrailingIcon(expanded = dropDownMenuOption, size = 23.dp, tint = textAndIconColor)
            }
            ExposedDropdownMenu(
                scrollState = rememberScrollState(),
                modifier = Modifier
                    .width(150.dp)
                    .background(Color.White)
                    .heightIn(max = 200.dp),
                expanded = dropDownMenuOption,
                onDismissRequest = { toggleDropDownMenuOption() }
            ) {
                menuItems.forEach { item ->

                    DropdownMenuItem(

                        text = {
                            Text(
                                text = item.title ?:"test",
                                style = TextStyle.Default.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        onClick = {
                            onValueChange(item)
                            toggleDropDownMenuOption()
                        },

                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiskyOptionDropDownMenuComponent(
    modifier: Modifier,
    toggleDropDownMenuOption: () -> Unit,
    dropDownMenuState:Boolean,
    menuItems: List<WhiskyOptionItems>,
    onClick:(WhiskyOptionItems)->Unit,
) {

    Log.d("상태",dropDownMenuState.toString())
    Column(
        modifier=modifier
    ) {
        IconButton(
            onClick = { toggleDropDownMenuOption() },
            modifier=Modifier.size(30.dp).padding(top=5.dp,end=5.dp)
        ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "",
            modifier = Modifier
                .size(25.dp),
        ) }
        DropdownMenu(
            expanded = dropDownMenuState,
            onDismissRequest = { toggleDropDownMenuOption() }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.title,
                            style = TextStyle.Default.copy(
                                color = Color.Gray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    onClick = {
                        onClick(item)
                        toggleDropDownMenuOption()
                    },
                    modifier=Modifier.height(40.dp)
                )
            }
        }
//
//
//        ExposedDropdownMenuBox(
//            expanded = dropDownMenuState,
//            onExpandedChange = { toggleDropDownMenuOption() },
//            modifier = Modifier
//                .size(30.dp)
//        ) {
//
//
//            Icon(
//                imageVector = Icons.Default.MoreVert,
//                contentDescription = "",
//                modifier = Modifier
//                    .size(30.dp)
//                    .menuAnchor(),
//            )
//
//
//            ExposedDropdownMenu(
//                scrollState = rememberScrollState(),
//                modifier = Modifier
//                    .width(150.dp)
//                    .background(Color.White)
//                    .heightIn(max = 170.dp),
//                expanded = dropDownMenuState,
//                onDismissRequest = { toggleDropDownMenuOption() }
//            ) {
//                menuItems.forEach { item ->
//                    DropdownMenuItem(
//                        text = {
//                            Text(
//                                text = item.title,
//                                style = TextStyle.Default.copy(
//                                    color = Color.Gray,
//                                    fontSize = 11.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                        },
//                        onClick = {
//                            toggleDropDownMenuOption()
//                        },
//                    )
//                }
//            }
//        }
//    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCountryDropDownMenuComponent(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    dropDownMenuOption: Boolean,
    toggleDropDownMenuOption: () -> Unit,
    menuItems: List<CountryItems>,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) MainColor else Color.White, label = ""
    )

    val selectBorder=  if (dropDownMenuOption) 1.5.dp else 0.5.dp


    val textAndIconColor by animateColorAsState(
        targetValue = if (dropDownMenuOption) LightBlackColor else Color.LightGray, label = ""
    )

    Column(
        modifier=modifier
    ) {

        ExposedDropdownMenuBox(
            expanded = dropDownMenuOption,
            onExpandedChange = { toggleDropDownMenuOption() },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(
                        selectBorder,
                        Color.LightGray
                    ),
                    RoundedCornerShape(8.dp)
                )
                .widthIn(max=140.dp)
                .height(42.dp)
                .background(backgroundColor)

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    ,
                verticalAlignment = Alignment.CenterVertically
            ){

                BasicTextField(
                    value = value,
                    onValueChange = {
                        onValueChange(it)
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .width(95.dp)
                        .height(45.dp)
                        .padding(start = 1.dp, end = 2.dp),

                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier

                                ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(5.dp))

                            Box(
                                contentAlignment = Alignment.CenterStart
                            ){
                                innerTextField()
                            }

                        }
                    },
                    singleLine = true
                )
//                Spacer(modifier = Modifier.width(5.dp))


                CustomTrailingIcon(expanded = dropDownMenuOption, size = 23.dp, tint = textAndIconColor,modifier.weight(1f).menuAnchor())
            }
            ExposedDropdownMenu(
                scrollState = rememberScrollState(),
                modifier = Modifier
                    .width(150.dp)
                    .background(Color.White)
                    .heightIn(max = 180.dp),
                expanded = dropDownMenuOption,
                onDismissRequest = { toggleDropDownMenuOption() }
            ) {
                menuItems.forEach { item ->

                    DropdownMenuItem(

                        text = {
                            Text(
                                text = item.country,
                                style = TextStyle.Default.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        onClick = {
                            onValueChange(item.country)
                            toggleDropDownMenuOption()
                        },
                        trailingIcon = {
                            Image(
                                imageVector = ImageVector.vectorResource(item.icon),
                                contentDescription = null,

                                modifier = Modifier.size(20.dp)

                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {

    val testList= listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder)
    val dropDownMenuItems=listOf(WhiskyOptionItems.DeleteWhisky)

    SelectCountryDropDownMenuComponent(
        value = "test",
        onValueChange = {

        },
        dropDownMenuOption = false,
        toggleDropDownMenuOption = {  },
        menuItems = countryData,
        modifier = Modifier
    )
}