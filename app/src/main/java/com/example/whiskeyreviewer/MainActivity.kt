package com.example.whiskeyreviewer

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.component.customComponent.ProgressIndicatorDialog
import com.example.whiskeyreviewer.component.home.InsertWhiskyDetailDialog
import com.example.whiskeyreviewer.nav.MainNavGraph
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    FullSizeProgressIndicator()

                    Greeting(ssaId,mainViewModel)
                }
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



//    MainNavGraph(mainNavController,writeReviewViewModel,mainViewModel)

    MainNavGraph(mainNavController,writeReviewViewModel,mainViewModel)

//    when(mainViewModel.loginResult.value){
//        true -> {
//            MainNavGraph(mainNavController,writeReviewViewModel,mainViewModel)
//        }
//        false -> { }
//    }




//    InsertReviewView()

//    TapLayoutComponent()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhiskeyReviewerTheme {
//        Greeting("Android", mainViewModel)
    }
}

