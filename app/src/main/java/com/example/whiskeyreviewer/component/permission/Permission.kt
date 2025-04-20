package com.example.whiskeyreviewer.component.permission

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import android.content.Intent

import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Camera
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.whiskeyreviewer.data.PermissionInfo
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionItem(
    permissionInfo: PermissionInfo,
    permissionState: PermissionState,
    manualSettingClick:()->Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = permissionInfo.imageVector,
            contentDescription = null,
            Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = permissionInfo.title,
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "권한 수동 설정",
                    style = TextStyle.Default.copy(
                        color = LightBlackColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(end = 5.dp)
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
                            manualSettingClick()
                        }
                )
            }

            Text(
                text = permissionInfo.description,
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            if (permissionState.status.isGranted) {
                Text(text = "허용됨",
                    style = TextStyle.Default.copy(
                        color = Color(0xFF60C564),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            } else {
                if (permissionState.status.shouldShowRationale) {
                    Text(
                        text = "권한을 다시 확인해 주세요",
                        style = TextStyle.Default.copy(
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                } else {

                    Text(
                        text = "거부됨",
                        style = TextStyle.Default.copy(
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun rememberPermissionList(): List<PermissionInfo> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            PermissionInfo(
                permission = android.Manifest.permission.CAMERA,
                title = "카메라 권한",
                description = "촬영을 위해서는 카메라 권한이 필요합니다.",
                isRequired = true,
                imageVector = Icons.Default.Camera
            )
        )
    } else {
        listOf(
            PermissionInfo(
                permission = android.Manifest.permission.CAMERA,
                title = "카메라 권한",
                description = "촬영을 위해서는 카메라 권한이 필요합니다.",
                isRequired = true,
                imageVector = Icons.Default.Camera
            )
        )
    }
}


//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequiredPermission() {
//    val state = rememberPermissionState(android.Manifest.permission.CAMERA)
//    Scaffold {
//        when {
//            state.status.isGranted -> CameraScreen()
//            else -> {
//                LaunchedEffect(Unit) {
//                    state.launchPermissionRequest()
//                }
//                PermissionRationale(state)
//            }
//        }
//    }
//}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRationale(state: PermissionState) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(Modifier.padding(vertical = 120.dp, horizontal = 16.dp)) {

            Icon(Icons.Rounded.Camera, contentDescription = null, tint = MaterialTheme.colors.onBackground)

            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier,
                text = "카메라 권한 확인.",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier,
                text = "사진 촬영을 위해서는 카메라 권한이 필요합니다.",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }

            }
        ) {
            Text("Go to settings")
        }
    }
}