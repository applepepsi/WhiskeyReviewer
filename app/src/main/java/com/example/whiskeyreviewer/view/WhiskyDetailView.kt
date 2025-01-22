package com.example.whiskeyreviewer.view

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.example.whiskeyreviewer.component.customComponent.CustomFloatingActionButton
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customComponent.EmptyReviewDataComponent
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailBottleNumDropDownMenuComponent
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailDropDownMenuComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.home.ConfirmDialog
import com.example.whiskeyreviewer.component.home.ImageTypeSelectDialog
import com.example.whiskeyreviewer.component.home.ImageViewerDialog
import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.component.myReview.MyReviewGraphComponent2
import com.example.whiskeyreviewer.component.myReview.MyReviewPost
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.data.FloatingActionButtonItems
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.MainRoute.REVIEW_DETAIL
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import java.io.File

@Composable
fun WhiskeyDetailView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scrollState= rememberScrollState()
    val context = LocalContext.current
    val imageCapture = remember { ImageCapture.Builder().build() }


    var imageFile by remember { mutableStateOf<File?>(null) }


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                mainViewModel.setSelectedWhiskyImage(uri)
            }
        }
    )

//    LaunchedEffect(mainViewModel.selectedWhiskyImageUri.value) {
//        Log.d("값", mainViewModel.selectedWhiskyImageUri.value.toString())
//
//        if(mainViewModel.selectedWhiskyImageUri.value != Uri.EMPTY){
//            mainViewModel.toggleSelectedWhiskyDialogState()
//        }
//    }

//    if(mainViewModel.cameraState.value){
//        CameraComponent(
//            mainViewModel = mainViewModel,
//        )
//    }
    if(mainViewModel.errorToastState.value) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = mainViewModel.errorToastIcon.value)
        mainViewModel.resetToastErrorState()
    }


    ConfirmDialog(
        title = "위스키 이미지 변경",
        text = "대표 이미지를 변경 하시겠습니까?",
        confirm = {
            mainViewModel.toggleSelectedWhiskyDialogState()
            mainViewModel.toggleImageTypeSelectDialogState()
        },
        toggleOption = { mainViewModel.toggleSelectedWhiskyDialogState() },
        currentState = mainViewModel.selectedWhiskyImageUriState.value
    )

    ImageTypeSelectDialog(
        albumSelectState = mainViewModel.imageTypeSelectState.value.albumSelected,
        cameraSelectState = mainViewModel.imageTypeSelectState.value.cameraSelected,
        confirm = {
            when {
                mainViewModel.imageTypeSelectState.value.albumSelected -> {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }
                mainViewModel.imageTypeSelectState.value.cameraSelected -> {

                    mainViewModel.setCameraTag(AddImageTag.ChangeWhiskyImage)
                    navController.navigate(MainRoute.CAMERA)
                }

                else -> {}
            }.also {
                mainViewModel.toggleImageTypeSelectDialogState()
            }
        },
        onSelect = { mainViewModel.updateSelectImageType(it) },
        toggleOption = {mainViewModel.toggleImageTypeSelectDialogState()},
        currentState = mainViewModel.imageTypeSelectDialogState.value
    )

    ImageViewerDialog(
        currentImage = mainViewModel.selectImageUrl.value,
        toggleOption = { mainViewModel.toggleImageDialogState() },
        currentState = mainViewModel.imageDialogState.value
    )

    ConfirmDialog(
        title = "리뷰 제거",
        text = "리뷰를 제거하시겠습니까?",
        confirm = { /*TODO*/ },
        toggleOption = { mainViewModel.toggleConfirmDialog() },
        currentState = mainViewModel.confirmDialogState.value
    )

    val info=WhiskyName(
        whisky_name = mainViewModel.selectWhiskyData.value.whisky_name,
        is_first = false,
        whisky_uuid = ""
    )


    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                expendState = mainViewModel.homeFloatingActionButtonState.value,
                floatingActionButtonClick = { mainViewModel.toggleHomeFloatingActionButtonState() },
                floatingActionItemClick = {
                    //이름 수정해야함

                    when(it.screenRoute){
                        FloatingActionButtonItems.OldBottle.screenRoute-> {
                            Log.d("루트",it.screenRoute)


                            mainViewModel.setWriteReviewWhiskyInfo(info, bottleNum = mainViewModel.currentMyReviewBottleNumFilter.value)

//                            mainViewModel.setCurrentBottleNum(mainViewModel.currentMyReviewBottleNumFilter.value)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        FloatingActionButtonItems.NewBottle.screenRoute-> {
                            Log.d("루트",it.screenRoute)
                            mainViewModel.setWriteReviewWhiskyInfo(info, bottleNum = mainViewModel.currentMyReviewBottleNumFilter.value+1)

//                            mainViewModel.setCurrentBottleNum(mainViewModel.currentMyReviewBottleNumFilter.value+1)
                            navController.navigate(MainRoute.INSERT_REVIEW)
                        }
                        else-> Log.d("루트",it.screenRoute)
                    }
                },
                items=
                    listOf(
                        FloatingActionButtonItems.OldBottle,
                        FloatingActionButtonItems.NewBottle
                    )

            )
        },

    ) {
    Column(
        modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {

        CustomAppBarComponent(
            titleTextValue = "나의 리뷰",
            leftButton = {
                CustomIconComponent(
                    icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                    onClick = {
                        navController.popBackStack()
//                        navController.navigate(MainRoute.HOME) {
//                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                        }
                    },
                    modifier = Modifier
                )
            },
            rightButton = {
                Spacer(modifier = Modifier.size(35.dp))
            },
        )
        SingleWhiskeyComponent(
            singleWhiskeyData = SingleWhiskeyData(),
            reviewClick = {},
            deleteWhisky = {},
            showOption = false,
            imageClick={
                mainViewModel.toggleSelectedWhiskyDialogState()
            },
            imageClickAllow = true
        )


        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 8.dp),
            horizontalArrangement = Arrangement.End
        ){

            WhiskeyDetailBottleNumDropDownMenuComponent(

                value = mainViewModel.currentMyReviewBottleNumFilter.value,
                onValueChange = { mainViewModel.updateMyBottleNumFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.bottleNum,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                    MyReviewFilterItems.BOTTLE_NUM) },
                menuItems = (1..mainViewModel.myReviewData.value.bottleCount).toList()
            )

            Spacer(modifier = Modifier.width(10.dp))

            WhiskeyDetailDropDownMenuComponent(

                value = mainViewModel.currentMyReviewDayFilter.value,
                onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.day,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(MyReviewFilterItems.DAY) },
                menuItems = listOf(MyReviewFilterItems.New,MyReviewFilterItems.Old,)
            )

            Spacer(modifier = Modifier.width(10.dp))

            WhiskeyDetailDropDownMenuComponent(

                value = mainViewModel.currentMyReviewTypeFilter.value,
                onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.reviewType,
                toggleDropDownMenuOption = { mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(MyReviewFilterItems.REVIEW_TYPE) },
                menuItems = listOf(MyReviewFilterItems.Review,MyReviewFilterItems.Graph)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            when (mainViewModel.currentMyReviewTypeFilter.value) {

                MyReviewFilterItems.Graph -> {
                    if(mainViewModel.myReviewDataList.value.isEmpty()){
                        EmptyReviewDataComponent(
                            text="리뷰가 존재하지 않습니다.",
                            icon=ImageVector.vectorResource(R.drawable.graph)
                        )
                    }else{
                        MyReviewGraphComponent2(
                            mainViewModel.myReviewDataList.value
                        )
                    }
                }

                MyReviewFilterItems.Review -> {
                    if(mainViewModel.myReviewDataList.value.isEmpty()){
                        EmptyReviewDataComponent(
                            text="리뷰가 존재하지 않습니다.",
                            icon=ImageVector.vectorResource(R.drawable.empty_bottle)
                        )
                    }else {
                        MyReviewPost(
                            reviewDataList = mainViewModel.myReviewDataList.value,
                            singleReviewClick = {
                                mainViewModel.setSelectReviewData(it)
                                navController.navigate(REVIEW_DETAIL)
                            },
                            onImageSelect = {
                                mainViewModel.setSelectImage(it)
                                mainViewModel.toggleImageDialogState()
                            },
                            deleteReview = {

                                mainViewModel.toggleConfirmDialog()
                            },
                            //todo 지금 구조가 너무 난잡함 하나의 데이터로 통합하던지 해야함
                            modifyReview={whiskyReviewData->

                                writeReviewViewModel.synchronizationWhiskyData(
                                    whiskyReviewData,
                                    bottleNum=mainViewModel.currentMyReviewBottleNumFilter.value
                                )
                                val info=WhiskyName(
                                    whisky_name = SingleWhiskeyData().whisky_name,
                                    is_first = false,
                                    whisky_uuid = ""
                                )

                                mainViewModel.setWriteReviewWhiskyInfo(
                                    info,
                                    bottleNum = mainViewModel.currentMyReviewBottleNumFilter.value)

                                navController.navigate(MainRoute.INSERT_REVIEW)
                            }
                        )
                    }
                }

                MyReviewFilterItems.New -> TODO()
                MyReviewFilterItems.Old -> TODO()
            }

            }
        }

    }

}

//val testReviewDataList = listOf(
//    WriteReviewData(
//        content = "스타일 A",
//        is_anonymous = false,
//        open_date = LocalDate.of(2024, 1, 10),
//        tags = listOf(""),
//        score = 4.0
//    ),
//    WriteReviewData(
//        content = "스타일 B",
//        is_anonymous = true,
//        open_date = LocalDate.of(2025, 2, 15),
//        tags = listOf(""),
//        score = 3.0
//    ),
//    WriteReviewData(
//        content = "스타일 C",
//        is_anonymous = false,
//        open_date = LocalDate.of(2022, 3, 20),
//        tags = listOf(""),
//        score = 5.0
//    ),
//    WriteReviewData(
//        content = "스타일 D",
//        is_anonymous = true,
//        open_date = LocalDate.of(2021, 4, 5),
//        tags = listOf(""),
//        score = 2.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 4.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 4.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 2.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 3.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 2.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 5.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 3.0
//    ),
//    WriteReviewData(
//        content = "스타일 E",
//        is_anonymous = false,
//        open_date = LocalDate.of(2020, 5, 30),
//        tags = listOf(""),
//        score = 1.0
//    ),
//)

@Preview(showBackground = true)
@Composable
fun WhiskeyDetailPreview() {
    val writeReviewViewModel: WriteReviewViewModel = hiltViewModel()
    val mainViewModel:MainViewModel= hiltViewModel()
    val mainNavController = rememberNavController()
    WhiskeyReviewerTheme {
        WhiskeyDetailView(writeReviewViewModel, mainNavController, mainViewModel)
    }
}