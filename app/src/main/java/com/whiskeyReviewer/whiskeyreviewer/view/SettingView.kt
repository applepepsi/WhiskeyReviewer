package com.whiskeyReviewer.whiskeyreviewer.view

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.whiskeyReviewer.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.whiskeyReviewer.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.whiskeyReviewer.whiskeyreviewer.component.home.NavigationDrawerLabel
import com.whiskeyReviewer.whiskeyreviewer.component.permission.PermissionItem
import com.whiskeyReviewer.whiskeyreviewer.component.permission.rememberPermissionList
import com.whiskeyReviewer.whiskeyreviewer.ui.theme.LightBlackColor
import com.whiskeyReviewer.whiskeyreviewer.viewModel.MainViewModel
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.whiskeyReviewer.whiskeyreviewer.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingView(
    mainViewModel:MainViewModel,
    navController: NavHostController,
){

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val permissionList = rememberPermissionList()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissionList.map { it.permission }
    )
    Scaffold(
        modifier = Modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
                .verticalScroll(scrollState)

        ) {
            CustomAppBarComponent(
                titleTextValue = "설정",
                leftButton = {
                    CustomIconComponent(
                        icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                    )
                },
                rightButton = {
                    Spacer(modifier = Modifier.size(35.dp))
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "권한 관리",
                style = TextStyle.Default.copy(
                    color = LightBlackColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=15.dp,bottom=3.dp)
            )

            permissionList.forEach { singlePermission ->
                PermissionItem(
                    permissionInfo = singlePermission,
                    permissionState = permissionsState.permissions.first {
                        it.permission == singlePermission.permission
                    },
                    manualSettingClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(intent)
                    }
                )
            }

            NavigationDrawerLabel(selectColor = Color.LightGray, modifier = Modifier.padding(horizontal = 15.dp))

        }
    }
}

