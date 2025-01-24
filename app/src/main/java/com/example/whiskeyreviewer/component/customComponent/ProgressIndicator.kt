package com.example.whiskeyreviewer.component.customComponent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ehsanmsz.mszprogressindicator.progressindicator.BallBeatProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallGridBeatProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseSyncProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallScaleRippleMultipleProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallTrianglePathProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScalePulseOutRapidProgressIndicator
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FullSizeProgressIndicator(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        BallPulseSyncProgressIndicator(
            modifier = Modifier,

            color = MainColor,
            animationDuration = 600,
            ballCount = 5,
            animationDelay = 500,
            spacing = 10.dp,
            ballDiameter = 15.dp,
            ballJumpHeight = 20.dp
        )
    }
}

@Composable
fun SmallSizeProgressIndicator(
    modifier: Modifier
){


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(

            modifier = modifier,
            color = MainColor,
            strokeWidth = 5.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressIndicatorDialog(
    text:String="",
    toggleOption: () -> Unit,
    currentState: Boolean = true,
){


    if (currentState) {
        Dialog(
            onDismissRequest = { toggleOption() },
            properties = ModalBottomSheetDefaults.properties.let {
                DialogProperties(
//                    decorFitsSystemWindows = false,
                    securePolicy = it.securePolicy,
                    usePlatformDefaultWidth = false
                )
            },
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ){
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BallPulseSyncProgressIndicator(
                        modifier = Modifier,

                        color = MainColor,
                        animationDuration = 600,
                        ballCount = 5,
                        animationDelay = 500,
                        spacing = 10.dp,
                        ballDiameter = 20.dp,
                        ballJumpHeight = 20.dp
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    if(text!=""){
                        Text(
                            text = text,
                            style = TextStyle.Default.copy(
                                color = LightBlackColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun ProgressBarPreview(){
    WhiskeyReviewerTheme {
//        ProgressIndicatorDialog(
//            toggleOption = {},
//            currentState = true,
//            text="로딩중"
//        )

        SmallSizeProgressIndicator(
            modifier = Modifier
        )
    }
}