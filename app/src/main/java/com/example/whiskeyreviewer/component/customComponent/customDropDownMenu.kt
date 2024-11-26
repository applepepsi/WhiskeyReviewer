package com.example.whiskeyreviewer.component.customComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.component.home.TapLayoutComponent
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
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
                modifier = Modifier.width(150.dp).background(Color.White),
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

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {

    val testList= listOf(WhiskeyFilterItems.DayAscendingOrder,WhiskeyFilterItems.DayDescendingOrder)

    WhiskeyReviewerTheme {
        CustomDropDownMenuComponent(
            value=WhiskeyFilterItems.DayAscendingOrder,
            onValueChange = {},
            dropDownMenuOption = false,
            toggleDropDownMenuOption = { /*TODO*/ },
            menuItems = testList,

        )
    }
}