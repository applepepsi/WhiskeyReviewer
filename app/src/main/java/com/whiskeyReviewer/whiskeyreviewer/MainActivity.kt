package com.whiskeyReviewer.whiskeyreviewer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whiskeyReviewer.whiskeyreviewer.component.Ad.AdMobBannerAd
import com.whiskeyReviewer.whiskeyreviewer.component.dialog.AllDialogs
import com.whiskeyReviewer.whiskeyreviewer.component.permission.rememberPermissionList
import com.whiskeyReviewer.whiskeyreviewer.data.MainRoute
import com.whiskeyReviewer.whiskeyreviewer.nav.MainNavGraph
import com.whiskeyReviewer.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.whiskeyReviewer.whiskeyreviewer.utils.FirstRunCheck
import com.whiskeyReviewer.whiskeyreviewer.viewModel.MainViewModel
import com.whiskeyReviewer.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("HardwareIds", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        MobileAds.initialize(this)
        val ssaId=Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)


        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {

            val mainViewModel: MainViewModel = hiltViewModel()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                mainViewModel.tryLogin( ssaid = ssaId )
            }

            splashScreen.setKeepOnScreenCondition{
                !mainViewModel.loginResult.value
            }
            WhiskeyReviewerTheme(darkTheme = false) {
                    if(mainViewModel.loginResult.value){
                        Log.d("뷰 가져오기 시작","로그인 시작3")
                        Greeting(ssaId,mainViewModel,context)
                    }
                }

            }
        }

}

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting(ssaId: String, mainViewModel: MainViewModel, context: Context) {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    val mainNavController = rememberNavController()

    val permissionList = rememberPermissionList()

    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissionList.map { it.permission }
    )

    LaunchedEffect(Unit) {
        val firstRunCheckResult=FirstRunCheck.firstRunCheck(context = context)

        if(firstRunCheckResult){
            permissionsState.launchMultiplePermissionRequest()
        }

    }




    AllDialogs(
        mainViewModel=mainViewModel,
        writeReviewViewModel=writeReviewViewModel,
        mainNavController = mainNavController
    )


    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(currentRoute!=MainRoute.WHISKY_DETAIL && currentRoute!="${MainRoute.INSERT_REVIEW}/{tag}"){
                AdMobBannerAd()
            }
        }
    ) { paddingValues->
        Column(
            Modifier.padding(paddingValues)
        ) {
            MainNavGraph(mainNavController,writeReviewViewModel,mainViewModel)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhiskeyReviewerTheme {
//        Greeting("Android", mainViewModel)
    }
}

