package com.example.whiskeyreviewer.component.customComponent

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.viewModel.MainViewModel

@Composable
fun LongTextInputComponent(
    text:String,
    updateText:(String)->Unit,
    helpText:String,

){
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(
                BorderStroke(
                    width = if (isFocused) 3.dp else 1.dp,
                    color = if (isFocused) MainColor else Color.LightGray
                ),
                RoundedCornerShape(8.dp)
            )

        ) {

        BasicTextField(
            value = text,
            onValueChange = {
                updateText(it)
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 1.dp, end = 12.dp)
                .align(Alignment.BottomStart)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ){
                        if (text.isEmpty()) {
                            Text(
                                text = helpText,
                                color = LightBlackColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
//                                                    .padding(start = 8.dp)
                            )
                        }

                        innerTextField()
                    }



                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        )

    }
}

@Composable
fun StrengthInputComponent(
    mainViewModel:MainViewModel
){

    var isFocused by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = if (isFocused) 3.dp else 1.dp,
                    color = if (isFocused) MainColor else Color.LightGray
                ),
                RoundedCornerShape(8.dp)
            )
            .widthIn(max=140.dp)
            .height(42.dp)
        ,

        ){
        BasicTextField(
            singleLine = true,
            value = mainViewModel.customWhiskyData.value.strength,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {

//                                    val numericInput = input.toDoubleOrNull()
//                                    if (numericInput != null) {
//                                        strength = numericInput.toString()
//                                    }

                if (it.isEmpty()){
                    mainViewModel.updateStrength(it)

                } else {
                    when (it.toDoubleOrNull()) {
                        null -> mainViewModel.customWhiskyData.value.strength
                        else -> mainViewModel.updateStrength(it)
                    }
                }
            },

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 24.dp)
                .onFocusChanged {state->
                    isFocused = state.isFocused
                }
            ,


            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ){
                        if (mainViewModel.customWhiskyData.value.strength.isEmpty()) {
                            Text(
                                text = "도수",
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
//                                                    .padding(start = 8.dp)
                            )
                        }
                        innerTextField()

                    }


                }
            }
        )
        if (mainViewModel.customWhiskyData.value.strength.isNotEmpty()) {
            Text(
                text = "%",
                color = LightBlackColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 17.dp)

            )
        }
    }
}

@Composable
fun BottlingDateInputComponent(
    mainViewModel:MainViewModel
){
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = if (isFocused) 3.dp else 1.dp,
                    color = if (isFocused) MainColor else Color.LightGray
                ),
                RoundedCornerShape(8.dp)
            )
            .widthIn(max=140.dp)
            .height(42.dp)
        ,

        ){


        BasicTextField(
            singleLine = true,
            value = mainViewModel.customWhiskyData.value.bottled_year.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {

//                                    val numericInput = input.toDoubleOrNull()
//                                    if (numericInput != null) {
//                                        strength = numericInput.toString()
//                                    }

                if (it.isEmpty()){
                    mainViewModel.updateSaleYear(it)

                } else {
                    when (it.toIntOrNull()) {
                        null -> mainViewModel.customWhiskyData.value.bottled_year
                        else -> mainViewModel.updateSaleYear(it)
                    }
                }
            },

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 24.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },


            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ){
                        if (mainViewModel.customWhiskyData.value.bottled_year.toString().isEmpty()) {
                            Text(
                                text = "병입년도",
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
//                                                    .padding(start = 8.dp)
                            )
                        }
                        innerTextField()

                    }


                }
            }
        )
        if (mainViewModel.customWhiskyData.value.bottled_year.toString().isNotEmpty()) {
            Text(
                text = "년",
                color = LightBlackColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 17.dp)

            )
        }
    }
}

@Composable
fun ShortTextInputComponent(

    text:String,
    hint:String,
    updateText: (String) -> Unit,
    isModify:Boolean,
    clickable:()->Unit={}
){

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = if (isFocused) 3.dp else 1.dp,
                    color = if (isFocused) MainColor else Color.LightGray
                ),
                RoundedCornerShape(8.dp)
            )

            .widthIn(max=140.dp)
            .height(42.dp)
            .then(
                if (isModify) {
                    Modifier
                } else {
                    Modifier.clickable {
                        clickable()
                    }
                }
            )
        ,

        ){
        BasicTextField(
            singleLine = true,
            value = text,
            onValueChange = {

//                                    val numericInput = input.toDoubleOrNull()
//                                    if (numericInput != null) {
//                                        strength = numericInput.toString()
//                                    }
                updateText(it)

            },

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 24.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    Log.d("포커스", isFocused.toString())
                }
            ,


            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        contentAlignment = Alignment.CenterStart
                    ){
                        if (text.isEmpty()) {
                            Text(
                                text = hint,
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
//                                                    .padding(start = 8.dp)
                            )
                        }
                        innerTextField()

                    }


                }
            },
            enabled = isModify
        )
    }
}