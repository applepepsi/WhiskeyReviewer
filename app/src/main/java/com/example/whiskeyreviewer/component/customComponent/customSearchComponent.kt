package com.example.whiskeyreviewer.component.customComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.ui.theme.MainColor

@Composable
fun CustomSearchBoxComponent(
    text:String="",
    onValueChange: (String) -> Unit,
    search:()->Unit,
    deleteInputText:()->Unit
) {

    Box(
        modifier = Modifier
            .padding(start = 15.dp, end =15.dp,)
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
        ,
        contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                Text(
                    text = "",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            leadingIcon = {
                IconButton(onClick = {
                    search()
                }) {
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