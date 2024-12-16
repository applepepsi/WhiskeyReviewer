package com.example.whiskeyreviewer.component.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.example.whiskeyreviewer.component.myReview.SingleStar
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
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
    val context = LocalContext.current
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
                            fontWeight = FontWeight.Normal
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
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(top=25.dp),
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

) {

    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() }
        ) {
            Surface(
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),

                    ) {


                    Text(
                        text = "점수를 선택해 주세요.",
                        style = TextStyle.Default.copy(
                            color = LightBlackColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .padding(start = 17.dp)

                            .clickable {
                                toggleOption()
                            }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, bottom = 10.dp),
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
        GetBackupCodeDialog(toggleOption = {})
    }
}