package com.example.whiskeyreviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.nav.MainNavGraph
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.view.HomeView
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

        val ssaId=Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        super.onCreate(savedInstanceState)

        setContent {

            WhiskeyReviewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(ssaId)
                }
            }
        }
    }
}

@Composable
fun Greeting(ssaId: String) {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainNavController = rememberNavController()

    Log.d("ssaid",ssaId)

//    InsertReviewView()
    MainNavGraph(mainNavController,writeReviewViewModel)
//    TapLayoutComponent()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhiskeyReviewerTheme {
        Greeting("Android")
    }
}