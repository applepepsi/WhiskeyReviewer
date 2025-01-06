package com.example.whiskeyreviewer.component.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nextclass.utils.whiskeyData
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customComponent.ImageComponent
import com.example.whiskeyreviewer.component.customComponent.SearchBarDivider
import com.example.whiskeyreviewer.component.customComponent.WhiskeyFilterDropDownMenuComponent
import com.example.whiskeyreviewer.data.TapLayoutItems

import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel

@Composable
fun GetBackupCodeDialog(
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    backupCode:String="test",

) {

    val clipboardManager = LocalClipboardManager.current
    var toastState by remember { mutableStateOf(false) }

    if(toastState) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(text = "코드가 복사 되었습니다.", icon = R.drawable.success_icon)
        toastState = false
    }

    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {

            Box(
                modifier = Modifier
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .align(Alignment.TopStart),
                    ) {

                    Text(
                        text = "백업 코드 발급",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                    )
                    Text(
                        text = "해당 코드를 다른 디바이스에 입력해 주세요.",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)
                    )
                }



                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = backupCode,
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier

                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                clipboardManager.setText(AnnotatedString((backupCode)))
                                toastState=!toastState
                            }

                    )

                }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, bottom = 8.dp)
                            .align(Alignment.BottomEnd)
                            ,
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text(
                            text = "확인",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier

                                .clickable {
                                    toggleOption()
                                }
                        )

                    }

                }

            }

        }

    }



@SuppressLint("SuspiciousIndentation")
@Composable
fun InsertBackupCodeDialog(
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    submitBackupCode:(String)->Unit,
    submitResult:Boolean?=null,
    resetResult:()->Unit
) {


    var code by remember { mutableStateOf("") }
    val customToast = CustomToast(LocalContext.current)


        if(submitResult == true) {

            customToast.MakeText(text = "인증 되었습니다. 사용자 정보를 불러오는 중 입니다.", icon = R.drawable.success_icon)
            resetResult()
        }else if(submitResult == false){

            customToast.MakeText(text = "올바르지 않은 코드입니다. 다시 시도해 주세요", icon = R.drawable.fail_icon)
            resetResult()
        }



    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {

            Box(
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        .align(Alignment.TopStart),
                ) {

                    Text(
                        text = "백업 코드 인증",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                    )
                    Text(
                        text = "이전 디바이스에서 발급 받은 코드를 입력해 주세요",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)
                    )
                }


                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 30.dp)
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
                        .align(Alignment.Center)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),

                        value = code,
                        onValueChange = {
                            code = it
                        },
                        placeholder = {
                            Text(
                                text = "",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
//                        leadingIcon = {
//                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {

                                    submitBackupCode(code)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, bottom = 8.dp)
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text(
                            text = "확인",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier

                                .clickable {
                                    toggleOption()
                                }
                        )

                    }

            }

        }

    }

}

@Composable
fun SelectWhiskeyDialog(
    mainViewModel:MainViewModel,
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    submitWhiskey:()->Unit,
    text:String="",
    updateText:(String)->Unit,

) {



    val customToast = CustomToast(LocalContext.current)

    LaunchedEffect(Unit) {
        updateText("")
    }

//    val whiskeyData = listOf(
//        TapLayoutItems.AllWhiskey,
//        TapLayoutItems.AmericanWhiskey,
//        TapLayoutItems.Blend,
//        TapLayoutItems.BlendedGrain,
//        TapLayoutItems.BlendedMalt,
//        TapLayoutItems.Bourbon,
//        TapLayoutItems.CanadianWhiskey,
//        TapLayoutItems.Corn,
//        TapLayoutItems.Rice,
//        TapLayoutItems.Rye,
//        TapLayoutItems.SingleGrain,
//        TapLayoutItems.SingleMalt,
//        TapLayoutItems.SinglePotStill,
//        TapLayoutItems.Spirit,
//        TapLayoutItems.Tennessee,
//        TapLayoutItems.Wheat
//    )

    if(mainViewModel.errorToastState.value) {
        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = R.drawable.fail_icon)
        mainViewModel.resetToastErrorState()
    }


    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
                Column(
                    modifier = Modifier
                        .height(410.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(top = 20.dp),

                ) {

                    Text(
                        text = "위스키 선택",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                    )
                    Text(
                        text = "리뷰를 작성하려는 위스키를 선택해 주세요.",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)
                    )

                    WhiskeyFilterDropDownMenuComponent(
                        value = mainViewModel.currentWhiskyFilterType.value,
                        onValueChange = { mainViewModel.updateCurrentWhiskyFilterType(it) },
                        dropDownMenuOption = mainViewModel.currentWhiskyFilterDropDownState.value,
                        toggleDropDownMenuOption = { mainViewModel.toggleWhiskyFilterDropDownMenuState()},
                        menuItems = whiskeyData,
                        modifier = Modifier.padding(start=15.dp,top=10.dp)
                    )



                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp, top = 6.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
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
                                .padding(start = 1.dp, end = 12.dp),

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
                                                text = "위스키를 검색해 보세요.",
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



                                    SearchBarDivider(
                                        modifier = Modifier

                                            .padding(end = 5.dp)
                                    )

                                    IconButton(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .padding(start = 8.dp),
                                        onClick = {
                                            //서버에서 위스키 검색
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = MainColor,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }

                                }
                            }
                        )

                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .height(175.dp)
                    ) {
                        itemsIndexed(items= mainViewModel.dialogSelectWhiskyData.value){ index, item->


                            SelectWhiskyComponent(
                                whiskeyData=item,
                                onSelect = { mainViewModel.toggleDialogSelectWhiskyState(index) },
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 20.dp, bottom = 13.dp, top = 12.dp),

                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "확인",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    submitWhiskey()
                                }
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "취소",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    toggleOption()
                                }
                        )
                    }
                }
            }



    }

}


@Composable
fun SelectCustomWhiskeyDialog(
    mainViewModel:MainViewModel,
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    submitWhiskey:()->Unit,
    text:String="",
    updateText:(String)->Unit,

    resetResult:()->Unit
) {



    val customToast = CustomToast(LocalContext.current)

//    val whiskeyData = listOf(
//        TapLayoutItems.AllWhiskey,
//        TapLayoutItems.AmericanWhiskey,
//        TapLayoutItems.Blend,
//        TapLayoutItems.BlendedGrain,
//        TapLayoutItems.BlendedMalt,
//        TapLayoutItems.Bourbon,
//        TapLayoutItems.CanadianWhiskey,
//        TapLayoutItems.Corn,
//        TapLayoutItems.Rice,
//        TapLayoutItems.Rye,
//        TapLayoutItems.SingleGrain,
//        TapLayoutItems.SingleMalt,
//        TapLayoutItems.SinglePotStill,
//        TapLayoutItems.Spirit,
//        TapLayoutItems.Tennessee,
//        TapLayoutItems.Wheat
//    )

    if(mainViewModel.errorToastState.value) {

        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = R.drawable.fail_icon)
        mainViewModel.resetToastErrorState()
    }

    var strength by remember { mutableStateOf<String>("") }
    var saleYear by remember { mutableStateOf<String>("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                mainViewModel.setSelectedImage(uri)
            }
        }
    )


    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
                Column(
                    modifier = Modifier
                        .height(400.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(top = 20.dp),
                ) {

                    Text(
                        text = "직접 위스키 입력",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                    )
                    Text(
                        text = "리뷰를 작성하려는 위스키를 입력해 주세요.",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)
                    )

                    ImageComponent(
                        imageClick = {
                            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        image = mainViewModel.selectedImageUri.value,
                        modifier = Modifier.padding(start=17.dp,top=20.dp)
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ){
                        WhiskeyFilterDropDownMenuComponent(
                            value = mainViewModel.currentCustomWhiskyType.value,
                            onValueChange = { mainViewModel.updateCurrentCustomWhiskyType(it) },
                            dropDownMenuOption = mainViewModel.currentCustomWhiskyDropDownState.value,
                            toggleDropDownMenuOption = { mainViewModel.toggleCustomWhiskyDropDownMenuState()},
                            menuItems = whiskeyData,
                            modifier = Modifier
                        )
                        
                        Spacer(modifier = Modifier.width(7.dp))
                        
                        Box(
                            modifier = Modifier

                                .border(
                                    BorderStroke(0.5.dp, Color.LightGray),
                                    RoundedCornerShape(8.dp)
                                )
                                .width(80.dp)
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
                                    .padding(end = 24.dp),


                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Spacer(modifier = Modifier.width(8.dp))

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
                                        .padding(end = 8.dp)

                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(7.dp))

                        Box(
                            modifier = Modifier

                                .border(
                                    BorderStroke(0.5.dp, Color.LightGray),
                                    RoundedCornerShape(8.dp)
                                )
                                .width(80.dp)
                                .height(42.dp)
                            ,

                            ){
                            BasicTextField(
                                singleLine = true,
                                value = mainViewModel.customWhiskyData.value.sale_year,
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
                                            null -> mainViewModel.customWhiskyData.value.sale_year
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
                                    .padding(end = 24.dp),


                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Box(
                                            contentAlignment = Alignment.CenterStart
                                        ){
                                            if (mainViewModel.customWhiskyData.value.sale_year.isEmpty()) {
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
                            if (mainViewModel.customWhiskyData.value.sale_year.isNotEmpty()) {
                                Text(
                                    text = "년",
                                    color = LightBlackColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 8.dp)

                                )
                            }
                        }

                    }





                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp, top = 6.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
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
                                .padding(start = 1.dp, end = 12.dp),

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
                                                text = "등록하려는 위스키의 이름을 입력해 주세요.",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 20.dp, bottom = 13.dp, top = 12.dp),

                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = "확인",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier

                            .clip(
                                RoundedCornerShape(8.dp)
                            )

                            .clickable {
                                submitWhiskey()
                            }

                    )
                    
                    Spacer(modifier = Modifier.width(15.dp))
                    
                    Text(
                        text = "취소",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                toggleOption()
                            }
                    )
                }

            }

        }

    }

}



@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
//    val navHostController= rememberNavController()
    WhiskeyReviewerTheme {
        SelectCustomWhiskeyDialog(mainViewModel=mainViewModel,toggleOption = { /*TODO*/ }, submitWhiskey = {}, updateText = {}) {

        }
    }
}