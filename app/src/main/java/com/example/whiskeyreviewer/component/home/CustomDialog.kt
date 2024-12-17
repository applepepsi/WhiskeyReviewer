package com.example.whiskeyreviewer.component.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customComponent.SearchBarDivider
import com.example.whiskeyreviewer.component.myReview.SingleStar
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import kotlinx.coroutines.launch

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

            customToast.MakeText(text = "올바르지 않은 코드입니다. 다시 시도해 주세요", icon = R.drawable.success_icon)
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



@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
//    val mainViewModel: MainViewModel = hiltViewModel()
//    val navHostController= rememberNavController()
    WhiskeyReviewerTheme {
        InsertBackupCodeDialog(toggleOption = {}, submitBackupCode = {}, resetResult = {})
    }
}