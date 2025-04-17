package com.example.whiskeyreviewer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.component.Ad.AdMobBannerAd
import com.example.whiskeyreviewer.component.camea.CameraDialog
import com.example.whiskeyreviewer.component.customComponent.ProgressIndicatorDialog
import com.example.whiskeyreviewer.component.home.ConfirmDialog
import com.example.whiskeyreviewer.component.home.InsertWhiskyDetailDialog
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.nav.MainNavGraph
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.GlobalNavigationHandler
import com.example.whiskeyreviewer.utils.GlobalNavigator
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        MobileAds.initialize(this)
        val ssaId=Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                mainViewModel.tryLogin( ssaid = ssaId )
            }

            splashScreen.setKeepOnScreenCondition{
                !mainViewModel.loginResult.value
            }
            WhiskeyReviewerTheme {
                // A surface container using the 'background' color from the theme

                    Greeting(ssaId,mainViewModel)
                }

            }
        }


    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting(ssaId: String, mainViewModel: MainViewModel) {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()

    val mainNavController = rememberNavController()
    val context = LocalContext.current

    Log.d("ssaid",ssaId)

    ProgressIndicatorDialog(
        toggleOption = {  mainViewModel.toggleProgressIndicatorState(state=false,text="")  },
        currentState = mainViewModel.progressIndicatorState.value,
        text=mainViewModel.progressIndicatorText.value
    )

    InsertWhiskyDetailDialog(
        toggleOption = { mainViewModel.toggleInsertWhiskyState() },
        currentState = mainViewModel.insertWhiskyDetailDialogState.value,
        text=mainViewModel.customWhiskyData.value.korea_name,
        whiskyEngName = mainViewModel.customWhiskyData.value.english_name,
        updateWhiskyEngName={
            mainViewModel.updateWhiskyEngName(it)
        },
        submitWhiskey = {
            mainViewModel.submitWhiskyDetail()
        },
        updateText = { mainViewModel.updateCustomWhiskyName(it) },
        resetResult = {},
        mainViewModel = mainViewModel,
        navController=mainNavController,
        updateTagText = {
            mainViewModel.updateWhiskyTagText(it)
        },
        tagText = mainViewModel.customWhiskyData.value.memo
    )

    ConfirmDialog(
        title = "위스키 제거",
        text = "위스키를 제거하시겠습니까?",
        confirm = { mainViewModel.deleteWhisky() },
        toggleOption = { mainViewModel.toggleDeleteWhiskyConfirmDialog() },
        currentState = mainViewModel.deleteWhiskyConfirmDialogState.value
    )

    CameraDialog(
       mainViewModel=mainViewModel,
        writeReviewViewModel=writeReviewViewModel,
        state=mainViewModel.cameraState.value,
        tag = mainViewModel.cameraTag.value,
    )

    LaunchedEffect(mainViewModel.backupCodeResult.value) {
        if(mainViewModel.backupCodeResult.value == true){
            val intent=context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.let {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(intent)
                Runtime.getRuntime().exit(0)
            }

        }
    }

    //테스트 해봐야함
    DisposableEffect(Unit) {
        GlobalNavigator.registerHandler(object : GlobalNavigationHandler {
            override fun retryLogin()
            {
                mainViewModel.tryLogin(ssaId)
            }
        })
        onDispose {
            GlobalNavigator.unregisterHandler()
        }
    }


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

