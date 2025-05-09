package com.example.whiskeyreviewer.component.dialog

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nextclass.utils.countryData
import com.example.nextclass.utils.whiskeyData
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.BottlingDateInputComponent
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customComponent.EmptyWhiskySearchComponent
import com.example.whiskeyreviewer.component.customComponent.ImageComponent
import com.example.whiskeyreviewer.component.customComponent.LongTextInputComponent
import com.example.whiskeyreviewer.component.customComponent.SearchBarDivider
import com.example.whiskeyreviewer.component.customComponent.SelectCountryDropDownMenuComponent
import com.example.whiskeyreviewer.component.customComponent.ShortTextInputComponent
import com.example.whiskeyreviewer.component.customComponent.SmallSizeProgressIndicator
import com.example.whiskeyreviewer.component.customComponent.StrengthInputComponent
import com.example.whiskeyreviewer.component.customComponent.WhiskeyFilterDropDownMenuComponent
import com.example.whiskeyreviewer.component.home.SelectWhiskyComponent
import com.example.whiskeyreviewer.component.writeReivew.SelectDateBottomSheet
import com.example.whiskeyreviewer.data.ImageData
import com.example.whiskeyreviewer.data.ImageSelectType
import com.example.whiskeyreviewer.data.TapLayoutItems

import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSettings

@Composable
fun GetBackupCodeDialog(
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    backupCode:String?=null,
    getBackupCode:()->Unit,
    getBackupCodeState:Boolean,
//    remainingTime:Int,
) {

    val clipboardManager = LocalClipboardManager.current
    var toastState by remember { mutableStateOf(false) }

    if(toastState) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(text = "코드가 복사 되었습니다.", icon = R.drawable.success_icon)
        toastState = false
    }



    val backupCodeData=backupCode ?:"코드 발급 실패"

    if (currentState) {
        LaunchedEffect(Unit) {
            getBackupCode()
        }

        Dialog(
            onDismissRequest = { toggleOption() }
        ) {

            Box(
                modifier = Modifier
                    .height(190.dp)
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
                    if(getBackupCodeState){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(35.dp),
                            color = MainColor,
                            strokeWidth = 3.dp,
                            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                        )
                    }else{
                        Text(
                            text = backupCodeData,
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
                                    clipboardManager.setText(AnnotatedString((backupCodeData)))
                                    toastState=!toastState
                                }

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "5분 이내에 코드를 입력해 주세요.",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier

                        )
                    }

                }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, bottom = 12.dp)
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
            //todo 다시켜야함
            customToast.MakeText(text = "인증 되었습니다. 사용자 정보를 불러오는 중 입니다.", icon = R.drawable.success_icon)
            resetResult()
        }else if(submitResult == false){

            customToast.MakeText(text = "유효하지 않은 코드입니다. 다시 시도해 주세요", icon = R.drawable.fail_icon)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectWhiskeyDialog(
    mainViewModel: MainViewModel,
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    submitWhiskey: () -> Unit,
    text: String = "",
    updateText: (String) -> Unit,
    searchWhisky: () -> Unit,
    toggleInsertDetailDialog:()->Unit={},
    selfInput:()->Unit,
    ) {

    val customToast = CustomToast(LocalContext.current)

    LaunchedEffect(currentState) {
        mainViewModel.resetAddWhiskyDialog()
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
        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = mainViewModel.errorToastIcon.value)
        mainViewModel.resetToastErrorState()
    }


    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
                Column(
                    modifier = Modifier
                        .height(440.dp)
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
                            singleLine = true,
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
                                        modifier = Modifier.width(220.dp),
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
                                            searchWhisky()
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
                        if(mainViewModel.smallProgressIndicatorState.value){
                            Log.d("로딩상태",
                                mainViewModel.smallProgressIndicatorState.value.toString()
                            )
                            item{
                                SmallSizeProgressIndicator(modifier = Modifier
                                    .size(60.dp)
                                    .padding(top = 20.dp))
                            }
                        }
                        else if(mainViewModel.dialogSelectWhiskyData.value.isEmpty() && mainViewModel.searchButtonState.value){
                            item{
                                EmptyWhiskySearchComponent()
                            }
                        }else{
                            itemsIndexed(items= mainViewModel.dialogSelectWhiskyData.value){ index, item->
                                SelectWhiskyComponent(
                                    whiskeyData=item,
                                    onSelect = { mainViewModel.toggleDialogSelectWhiskyState(index) },
                                )
                            }
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "찾으시는 위스키가 없으신가요?",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier

                        )

                        Text(
                            text = "직접 입력",
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .drawBehind {
                                    val strokeWidthPx = 2.dp.toPx()
                                    val verticalOffset = size.height - 1.sp.toPx()
                                    drawLine(
                                        color = Color.LightGray,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticalOffset),
                                        end = Offset(size.width, verticalOffset)
                                    )
                                }
                                .clickable {
                                    selfInput()
                                }
                        )
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
//                                    submitWhiskey()
                                    //구조 변경으로 세부 입력 다이얼로그로 이동해야함
                                    //todo 세부 입력 다이얼로 이동하기 전에 정보 넘겨야함
                                    toggleInsertDetailDialog()
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


//@Composable
//fun SelectCustomWhiskeyDialog(
//    mainViewModel: MainViewModel,
//    toggleOption: () -> Unit,
//
//    currentState: Boolean = true,
//    submitWhiskey: () -> Unit,
//    text: String = "",
//    updateText: (String) -> Unit,
//
//    resetResult: () -> Unit,
//    navController: NavHostController
//) {
//
////    LaunchedEffect(currentState) {
////        mainViewModel.resetAddCustomWhiskyDialog()
////    }
//
//    val customToast = CustomToast(LocalContext.current)
//
////    val whiskeyData = listOf(
////        TapLayoutItems.AllWhiskey,
////        TapLayoutItems.AmericanWhiskey,
////        TapLayoutItems.Blend,
////        TapLayoutItems.BlendedGrain,
////        TapLayoutItems.BlendedMalt,
////        TapLayoutItems.Bourbon,
////        TapLayoutItems.CanadianWhiskey,
////        TapLayoutItems.Corn,
////        TapLayoutItems.Rice,
////        TapLayoutItems.Rye,
////        TapLayoutItems.SingleGrain,
////        TapLayoutItems.SingleMalt,
////        TapLayoutItems.SinglePotStill,
////        TapLayoutItems.Spirit,
////        TapLayoutItems.Tennessee,
////        TapLayoutItems.Wheat
////    )
//
//    if(mainViewModel.errorToastState.value) {
//
//        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = mainViewModel.errorToastIcon.value)
//        mainViewModel.resetToastErrorState()
//    }
//
//
//    val photoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = { uri ->
//            if (uri != null) {
//                mainViewModel.setSelectedImage(uri)
//            }
//        }
//    )
//
//    ImageTypeSelectDialog(
//        albumSelectState = mainViewModel.imageTypeSelectState.value.albumSelected,
//        cameraSelectState = mainViewModel.imageTypeSelectState.value.cameraSelected,
//        confirm = {
//            when {
//                mainViewModel.imageTypeSelectState.value.albumSelected -> {
//                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//
//                }
//                mainViewModel.imageTypeSelectState.value.cameraSelected -> {
//
////                    mainViewModel.setCameraTag(AddImageTag.AddWhisky)
//                    navController.navigate("${MainRoute.CAMERA}/addWhisky")
//                }
//
//                else -> {}
//            }.also {
//                mainViewModel.toggleImageTypeSelectDialogState()
//            }
//        },
//        onSelect = { mainViewModel.updateSelectImageType(it) },
//        toggleOption = {mainViewModel.toggleImageTypeSelectDialogState()},
//        currentState = mainViewModel.imageTypeSelectDialogState.value
//    )
//
//    if (currentState) {
//        Dialog(
//            onDismissRequest = { toggleOption() }
//        ) {
//                Column(
//                    modifier = Modifier
//                        .height(400.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color.White)
//                        .padding(top = 20.dp),
//                ) {
//
//                    Text(
//                        text = "직접 위스키 입력",
//                        style = TextStyle.Default.copy(
//                            color = LightBlackColor,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        ),
//                        modifier = Modifier
//                            .padding(start = 17.dp)
//
//                    )
//                    Text(
//                        text = "리뷰를 작성하려는 위스키를 입력해 주세요.",
//                        style = TextStyle.Default.copy(
//                            color = LightBlackColor,
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.Normal
//                        ),
//                        modifier = Modifier
//                            .padding(start = 17.dp)
//                    )
//
//                    ImageComponent(
//                        imageClick = {
//                            mainViewModel.toggleImageTypeSelectDialogState()
//                        },
//                        image = mainViewModel.selectedImageUri.value,
//                        modifier = Modifier.padding(start=17.dp,top=20.dp)
//                    )
//
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 15.dp, top = 10.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//
//                    ){
//                        WhiskeyFilterDropDownMenuComponent(
//                            value = mainViewModel.currentCustomWhiskyType.value,
//                            onValueChange = { mainViewModel.updateCurrentCustomWhiskyType(it) },
//                            dropDownMenuOption = mainViewModel.currentCustomWhiskyDropDownState.value,
//                            toggleDropDownMenuOption = { mainViewModel.toggleCustomWhiskyDropDownMenuState()},
//                            menuItems = whiskeyData,
//                            modifier = Modifier
//                        )
//
//                        Spacer(modifier = Modifier.width(7.dp))
//
//                        Box(
//                            modifier = Modifier
//
//                                .border(
//                                    BorderStroke(0.5.dp, Color.LightGray),
//                                    RoundedCornerShape(8.dp)
//                                )
//                                .width(80.dp)
//                                .height(42.dp)
//                            ,
//
//                        ){
//                            BasicTextField(
//                                singleLine = true,
//                                value = mainViewModel.customWhiskyData.value.strength,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                                onValueChange = {
//
////                                    val numericInput = input.toDoubleOrNull()
////                                    if (numericInput != null) {
////                                        strength = numericInput.toString()
////                                    }
//
//                                    if (it.isEmpty()){
//                                        mainViewModel.updateStrength(it)
//
//                                    } else {
//                                        when (it.toDoubleOrNull()) {
//                                            null -> mainViewModel.customWhiskyData.value.strength
//                                            else -> mainViewModel.updateStrength(it)
//                                        }
//                                    }
//                                },
//
//                                textStyle = TextStyle(
//                                    color = Color.Black,
//                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.Normal
//                                ),
//                                modifier = Modifier
//                                    .align(Alignment.CenterStart)
//                                    .padding(end = 24.dp),
//
//
//                                decorationBox = { innerTextField ->
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//
//                                        Spacer(modifier = Modifier.width(8.dp))
//
//                                        Box(
//                                            contentAlignment = Alignment.CenterStart
//                                        ){
//                                            if (mainViewModel.customWhiskyData.value.strength.isEmpty()) {
//                                                Text(
//                                                    text = "도수",
//                                                    color = Color.LightGray,
//                                                    fontSize = 12.sp,
//                                                    fontWeight = FontWeight.Normal,
//                                                    modifier = Modifier
////                                                    .padding(start = 8.dp)
//                                                )
//                                            }
//                                            innerTextField()
//
//                                        }
//
//
//                                    }
//                                }
//                            )
//                            if (mainViewModel.customWhiskyData.value.strength.isNotEmpty()) {
//                                Text(
//                                    text = "%",
//                                    color = LightBlackColor,
//                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.CenterEnd)
//                                        .padding(end = 8.dp)
//
//                                )
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.width(7.dp))
//
//                        Box(
//                            modifier = Modifier
//
//                                .border(
//                                    BorderStroke(0.5.dp, Color.LightGray),
//                                    RoundedCornerShape(8.dp)
//                                )
//                                .width(80.dp)
//                                .height(42.dp)
//                            ,
//
//                            ){
//                            BasicTextField(
//                                singleLine = true,
//                                value = mainViewModel.customWhiskyData.value.bottled_year.toString(),
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                                onValueChange = {
//
////                                    val numericInput = input.toDoubleOrNull()
////                                    if (numericInput != null) {
////                                        strength = numericInput.toString()
////                                    }
//
//                                    if (it.isEmpty()){
//                                        mainViewModel.updateSaleYear(it)
//
//                                    } else {
//                                        when (it.toIntOrNull()) {
//                                            null -> mainViewModel.customWhiskyData.value.bottled_year
//                                            else -> mainViewModel.updateSaleYear(it)
//                                        }
//                                    }
//                                },
//
//                                textStyle = TextStyle(
//                                    color = Color.Black,
//                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.Normal
//                                ),
//                                modifier = Modifier
//                                    .align(Alignment.CenterStart)
//                                    .padding(end = 24.dp),
//
//
//                                decorationBox = { innerTextField ->
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//
//                                        Spacer(modifier = Modifier.width(8.dp))
//
//                                        Box(
//                                            contentAlignment = Alignment.CenterStart
//                                        ){
//                                            if (mainViewModel.customWhiskyData.value.bottled_year.toString().isEmpty()) {
//                                                Text(
//                                                    text = "병입년도",
//                                                    color = Color.LightGray,
//                                                    fontSize = 12.sp,
//                                                    fontWeight = FontWeight.Normal,
//                                                    modifier = Modifier
////                                                    .padding(start = 8.dp)
//                                                )
//                                            }
//                                            innerTextField()
//
//                                        }
//
//
//                                    }
//                                }
//                            )
//                            if (mainViewModel.customWhiskyData.value.bottled_year.toString().isNotEmpty()) {
//                                Text(
//                                    text = "년",
//                                    color = LightBlackColor,
//                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.CenterEnd)
//                                        .padding(end = 8.dp)
//
//                                )
//                            }
//                        }
//
//                    }
//
//
//
//
//
//                    Box(
//                        modifier = Modifier
//                            .padding(start = 15.dp, end = 15.dp, top = 6.dp)
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(Color.White)
//                            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp)),
//                        contentAlignment = Alignment.Center
//                    ) {
//
//
//                        BasicTextField(
//                            value = text,
//                            onValueChange = {
//                                updateText(it)
//                            },
//                            textStyle = TextStyle(
//                                color = Color.Black,
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.Normal
//                            ),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(45.dp)
//                                .padding(start = 1.dp, end = 12.dp),
//
//                            decorationBox = { innerTextField ->
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth(),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//
//                                    Spacer(modifier = Modifier.width(10.dp))
//
//                                    Box(
//                                        contentAlignment = Alignment.CenterStart
//                                    ){
//                                        if (text.isEmpty()) {
//                                            Text(
//                                                text = "등록하려는 위스키의 이름을 입력해 주세요.",
//                                                color = LightBlackColor,
//                                                fontSize = 12.sp,
//                                                fontWeight = FontWeight.Normal,
//                                                modifier = Modifier
////                                                    .padding(start = 8.dp)
//                                            )
//                                        }
//
//                                        innerTextField()
//                                    }
//
//
//
//                                    Spacer(modifier = Modifier.weight(1f))
//
//                                }
//                            }
//                        )
//
//                    }
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                        .padding(end = 20.dp, bottom = 13.dp, top = 12.dp),
//
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.Bottom
//                ) {
//                    if(mainViewModel.tinyProgressIndicatorState.value){
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .padding(start = 5.dp)
//                                .size(25.dp),
//                            color = MainColor,
//                            strokeWidth = 3.dp,
//                            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(15.dp))
//
//                    Text(
//                        text = "확인",
//                        style = TextStyle.Default.copy(
//                            color = LightBlackColor,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Normal
//                        ),
//                        modifier = Modifier
//
//                            .clip(
//                                RoundedCornerShape(8.dp)
//                            )
//
//                            .clickable {
//                                submitWhiskey()
//                            }
//
//                    )
//
//                    Spacer(modifier = Modifier.width(15.dp))
//
//                    Text(
//                        text = "취소",
//                        style = TextStyle.Default.copy(
//                            color = LightBlackColor,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Normal
//                        ),
//                        modifier = Modifier
//                            .clip(
//                                RoundedCornerShape(8.dp)
//                            )
//                            .clickable {
//                                toggleOption()
//                            }
//                    )
//
//                }
//
//            }
//
//        }
//
//    }
//
//}

@Composable
fun InsertWhiskyDetailDialog(
    mainViewModel: MainViewModel,
    toggleOption: () -> Unit,

    currentState: Boolean = true,
    submitWhiskey: () -> Unit,
    text: String = "",
    updateText: (String) -> Unit,
    updateTagText:(String)->Unit,
    resetResult: () -> Unit,
    navController: NavHostController,
    whiskyEngName: String,
    updateWhiskyEngName:(String)->Unit,
    tagText:String,
) {

//    LaunchedEffect(currentState) {
//        mainViewModel.resetAddCustomWhiskyDialog()
//    }

    LaunchedEffect(Unit) {
        Log.d("mainViewModel.selectWhiskyData.value",
            mainViewModel.customWhiskyData.value.toString()
        )
        mainViewModel.selectWhiskyData.value
    }

    val customToast = CustomToast(LocalContext.current)


    val listState = rememberLazyListState()
    val customScrollbarSettings = ScrollbarSettings(
        thumbUnselectedColor = MainColor,
        thumbThickness = 6.dp,
        thumbMinLength = 0.1f,
        thumbMaxLength = 0.7f,
        alwaysShowScrollbar = true
    )

    if(mainViewModel.errorToastState.value) {

        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = mainViewModel.errorToastIcon.value)
        mainViewModel.resetToastErrorState()
    }




    if (mainViewModel.openDateBottomSheetState.value) {
        Log.d("바텀시트", mainViewModel.openDateBottomSheetState.value.toString())
        SelectDateBottomSheet(
            onDismiss = { mainViewModel.toggleOpenDateBottomSheetState() },
            updateSelectData = { mainViewModel.updateOpenDate(it) }
        )
    }



    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Column(
                modifier = Modifier
                    .height(600.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(top = 20.dp)

            ) {

                Text(
                    text = "세부 사항 입력",
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
                        .padding(start = 17.dp,bottom=5.dp)
                )

                LazyColumnScrollbar(
                    state = listState,
                    settings = customScrollbarSettings,
                    modifier=Modifier
                    ) {
                    LazyColumn(
                        modifier = Modifier,
                        state=listState,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ImageComponent(
                                    imageClick = {
                                        mainViewModel.toggleSingleImageTypeSelectDialogState()
                                    },
                                    image = mainViewModel.selectedImageUri.value,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "종류",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    WhiskeyFilterDropDownMenuComponent(
                                        value = mainViewModel.currentCustomWhiskyType.value,
                                        onValueChange = {
                                            mainViewModel.updateCurrentCustomWhiskyType(
                                                it
                                            )
                                        },
                                        dropDownMenuOption = mainViewModel.currentCustomWhiskyDropDownState.value,
                                        toggleDropDownMenuOption = { mainViewModel.toggleCustomWhiskyDropDownMenuState() },
                                        menuItems = whiskeyData.filter { it != TapLayoutItems.AllWhiskey },
                                        modifier = Modifier
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "국가",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    SelectCountryDropDownMenuComponent(
                                        value = mainViewModel.customWhiskyData.value.country,
                                        onValueChange = {
                                            mainViewModel.updateCurrentCountry(it)
                                        },
                                        dropDownMenuOption = mainViewModel.currentCountryDropDownMenuState.value,
                                        toggleDropDownMenuOption = { mainViewModel.toggleCurrentCountryDropDownMenuState() },
                                        menuItems = countryData,
                                        modifier = Modifier
                                    )
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "캐스크 타입",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    ShortTextInputComponent(
                                        text = mainViewModel.customWhiskyData.value.cask_type,
                                        hint = "캐스크 타입",
                                        updateText = {
                                            mainViewModel.updateCaskType(it)
                                        },
                                        isModify = true,
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "개봉일",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    ShortTextInputComponent(
                                        text = mainViewModel.customWhiskyData.value.open_date.toString(),
                                        hint = "개봉일",
                                        updateText = {},
                                        isModify = false,
                                        clickable = {
                                            mainViewModel.toggleOpenDateBottomSheetState()
                                        }
                                    )
                                }
                            }
                        }

                        item {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "도수",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    StrengthInputComponent(mainViewModel)
                                }
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "병입 년도",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            color = LightBlackColor
                                        ),
                                        modifier = Modifier
                                            .padding(start = 5.dp, bottom = 4.dp)
                                    )
                                    BottlingDateInputComponent(
                                        mainViewModel = mainViewModel
                                    )
                                }
                            }
                        }

                        item {
                            Column {
                                Text(
                                    text = "위스키 이름",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                        color = LightBlackColor
                                    ),
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                )
                                LongTextInputComponent(
                                    text = text,
                                    updateText = {
                                        updateText(it)
                                    },
                                    helpText = "등록하려는 위스키의 이름을 입력해 주세요.",
                                )
                            }
                        }

                        item {
                            Column {
                                Text(
                                    text = "위스키 영문 이름",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                        color = LightBlackColor
                                    ),
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                )
                                LongTextInputComponent(
                                    text = whiskyEngName,
                                    updateText = {
                                        updateWhiskyEngName(it)
                                    },
                                    helpText = "등록하려는 위스키의 영문 이름을 입력해 주세요."
                                )
                            }
                        }

                        item {
                            Column {
                                Text(
                                    text = "메모",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                        color = LightBlackColor
                                    ),
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                )
                                LongTextInputComponent(
                                    text = tagText,
                                    updateText = {
                                        updateTagText(it)
                                    },
                                    helpText = "메모 사항을 입력해 주세요."
                                )
                            }
                        }

                        item{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .padding(end = 20.dp, bottom = 13.dp, top = 7.dp),

                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                if(mainViewModel.tinyProgressIndicatorState.value){
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(start = 5.dp)
                                            .size(25.dp),
                                        color = MainColor,
                                        strokeWidth = 3.dp,
                                        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                                    )
                                }

                                Spacer(modifier = Modifier.width(15.dp))

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

        }

    }

}

@Composable
fun ConfirmDialog(
    title:String,
    text:String,
    confirm:()->Unit,
    toggleOption: () -> Unit,
    currentState: Boolean = true,

) {

    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Column(
                modifier = Modifier
                    .height(140.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(top = 20.dp),
            ) {

                Text(
                    text = title,
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp)
                )


                Text(
                    text = text,
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp,top=3.dp)
                )

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
                                confirm()
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
fun SingleImageTypeSelectDialog(
    albumSelectState: Boolean,
    cameraSelectState: Boolean,
    onSelect: (ImageSelectType) -> Unit,
    confirm: ()->Unit,
    toggleOption: () -> Unit,
    currentState: Boolean = true,
    ) {



    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Column(
                modifier = Modifier
                    .height(260.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(top = 20.dp),
            ) {

                Text(
                    text = "이미지 추가 방식",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp)
                )


                Text(
                    text = "이미지 추가 방식을 선택해 주세요.",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp,top=3.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    MethodSelectComponent(
                        icon=Icons.Default.PhotoLibrary,
                        onSelect = {
                            onSelect(ImageSelectType.ALBUM)
                        },
                        selectState = albumSelectState,
                        text="앨범"
                    )

                    MethodSelectComponent(
                        icon=Icons.Default.CameraAlt,
                        onSelect = {
                            onSelect(ImageSelectType.CAMERA)
                        },
                        selectState = cameraSelectState,
                        text="카메라"
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
                                confirm()
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
fun MultiImageTypeSelectDialog(
    albumSelectState: Boolean,
    cameraSelectState: Boolean,
    onSelect: (ImageSelectType) -> Unit,
    confirm: ()->Unit,
    toggleOption: () -> Unit,
    currentState: Boolean = true,
) {



    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Column(
                modifier = Modifier
                    .height(260.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(top = 20.dp),
            ) {

                Text(
                    text = "이미지 추가 방식",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp)
                )


                Text(
                    text = "이미지 추가 방식을 선택해 주세요.",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp,top=3.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    MethodSelectComponent(
                        icon=Icons.Default.PhotoLibrary,
                        onSelect = {
                            onSelect(ImageSelectType.ALBUM)
                        },
                        selectState = albumSelectState,
                        text="앨범"
                    )

                    MethodSelectComponent(
                        icon=Icons.Default.CameraAlt,
                        onSelect = {
                            onSelect(ImageSelectType.CAMERA)
                        },
                        selectState = cameraSelectState,
                        text="카메라"
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
                                confirm()
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
fun MethodSelectComponent(
    onSelect:()->Unit,
    icon:ImageVector,
    text:String,
    selectState:Boolean
){

    val itemColor=if(selectState) MainColor else Color.LightGray
    val backgroundColor=if(selectState) Color.White else Color.White

    Column(
        Modifier
            .height(100.dp)
            .width(100.dp)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onSelect()
            }
            .clip(
                RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(
                    1.dp,
                    itemColor
                ),
                RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = itemColor,
            modifier = Modifier.size(50.dp),
        )
        
//        Spacer(modifier = Modifier.height(5.dp))
        
        Text(
            text = text,
            style = TextStyle.Default.copy(
                color = itemColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewerDialog(
    currentImage: ImageData?=null,
    toggleOption: () -> Unit,
    currentState: Boolean = true,
){


    if (currentState) {

        Dialog(
            onDismissRequest = { toggleOption() },
            properties = properties.let {
                DialogProperties(

                    securePolicy = it.securePolicy,
                    usePlatformDefaultWidth = false
                )
            },
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
            ){
                IconButton(
                    onClick = {
                        toggleOption()
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopEnd)
                        .padding(end = 10.dp, top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(23.dp)
                    )

                }

                currentImage?.let { imageData ->

                    GlideImage(
                        imageModel = imageData.image,
                        modifier = Modifier
                            .size(350.dp)
                            .align(Alignment.Center)
                    )
                }

            }
        }
    }
}


@Composable
fun DetailSearchDialog(
    updateText: (String) -> Unit,
    text:String,
    toggleOption: () -> Unit,
    currentState: Boolean = true,
){

    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Column(
                modifier = Modifier
                    .height(185.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(top = 20.dp),

                ) {

                Text(
                    text = "세부 검색",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp)

                )
                Text(
                    text = "검색하려는 위스키의 세부 검색어를 입력해 주세요.",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(start = 17.dp)
                )


                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {


                    BasicTextField(
                        singleLine = true,
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
                                    modifier = Modifier,
                                    contentAlignment = Alignment.CenterStart
                                ){
                                    if (text.isEmpty()) {
                                        Text(
                                            text = "세부 검색어를 입력해 주세요.",
                                            color = LightBlackColor,
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

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 20.dp, bottom = 13.dp, top = 18.dp),

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
fun AddWhiskyDialogPreview() {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    val navHostController= rememberNavController()
    WhiskeyReviewerTheme {
        SelectWhiskeyDialog(
            mainViewModel = mainViewModel,
            toggleOption = { /*TODO*/ },
            submitWhiskey = { /*TODO*/ },
            updateText = {},
            searchWhisky = {}
        ) {
            
        }
    }
}