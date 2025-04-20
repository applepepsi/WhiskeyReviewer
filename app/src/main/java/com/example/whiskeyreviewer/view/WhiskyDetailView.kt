package com.example.whiskeyreviewer.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customComponent.EmptyReviewDataComponent
import com.example.whiskeyreviewer.component.customComponent.SmallSizeProgressIndicator
import com.example.whiskeyreviewer.component.customComponent.WhiskeyDetailDropDownMenuComponent
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.component.dialog.ConfirmDialog

import com.example.whiskeyreviewer.component.home.SingleWhiskeyComponent
import com.example.whiskeyreviewer.component.myReview.MyReviewGraphComponent2

import com.example.whiskeyreviewer.component.myReview.MySingleReviewComponent
import com.example.whiskeyreviewer.component.myReview.MyWhiskyDetailInfoComponent
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.data.MainRoute.REVIEW_DETAIL
import com.example.whiskeyreviewer.data.MyReviewFilterItems
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.ui.theme.WhiskeyReviewerTheme
import com.example.whiskeyreviewer.utils.ImageConverter
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun WhiskeyDetailView(
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                mainViewModel.setSelectedImage(uri)
            }
        }
    )

    ConfirmDialog(
        title = "위스키 제거",
        text = "위스키를 제거하시겠습니까?",
        confirm = {

        },
        toggleOption = { mainViewModel.toggleDeleteReviewConfirmDialog() },
        currentState = mainViewModel.deleteReviewConfirmDialogState.value
    )

    LaunchedEffect(Unit) {
        mainViewModel.getMyReviewList()
    }

    LaunchedEffect(mainViewModel.whiskyDeleteState.value) {
        if(mainViewModel.whiskyDeleteState.value){
            navController.navigateUp()
        }
    }

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
    if (mainViewModel.errorToastState.value) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(
            text = mainViewModel.errorToastMessage.value,
            icon = mainViewModel.errorToastIcon.value
        )
        mainViewModel.resetToastErrorState()
    }
    val height = remember { mutableStateOf(0.dp) }

    ConfirmDialog(
        title = "위스키 이미지 변경",
        text = "대표 이미지를 변경 하시겠습니까?",
        confirm = {
            mainViewModel.toggleSelectedWhiskyDialogState()
            mainViewModel.toggleSingleImageTypeSelectDialogState()
        },
        toggleOption = { mainViewModel.toggleSelectedWhiskyDialogState() },
        currentState = mainViewModel.selectedWhiskyImageUriState.value
    )


    ConfirmDialog(
        title = "리뷰 제거",
        text = "리뷰를 제거하시겠습니까?",
        confirm = {
            mainViewModel.toggleDeleteReviewConfirmDialog()
            mainViewModel.deleteReviewData()
        },
        toggleOption = { mainViewModel.toggleDeleteReviewConfirmDialog() },
        currentState = mainViewModel.deleteReviewConfirmDialogState.value
    )

//    val info=WhiskyName(
//        whisky_name = mainViewModel.selectWhiskyData.value.whisky_name,
//        is_first = false,
//        whisky_uuid = ""
//    )


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
                titleTextValue = "나의 리뷰",
                leftButton = {
                    CustomIconComponent(
                        icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                    )
                },
                rightButton = {
                    CustomIconComponent(
                        icon = ImageVector.vectorResource(R.drawable.half_bottle),
                        onClick = {
                            writeReviewViewModel.synchronizationWhiskyData(
                                WhiskyReviewData(
                                    review_uuid = mainViewModel.selectWhiskyData.value.whisky_uuid
                                ),
                                mainViewModel.selectWhiskyData.value.korea_name ?: mainViewModel.selectWhiskyData.value.english_name,
                                uriList = emptyList()
                            )

                            navController.navigate("${MainRoute.INSERT_REVIEW}/new")
                        },
                        modifier = Modifier
                    )
                }
            )

            SingleWhiskeyComponent(
                singleWhiskeyData = mainViewModel.selectWhiskyData.value,
                reviewClick = {},
                showOption = true,
                deleteWhisky = {

                    mainViewModel.updateSelectWhisky(it)
                    mainViewModel.toggleDeleteWhiskyConfirmDialog()
                },
                dropDownMenuState = mainViewModel.singleWhiskyDropDownMenuState.value,
                toggleDropDownMenuState = {mainViewModel.toggleSingleWhiskyDropDownMenuState()},
                imageClick = { mainViewModel.toggleSelectedWhiskyDialogState() },
                imageClickAllow = true,
                modifyWhiskyData = {

                    mainViewModel.modifyWhiskyMode(data=it)
                },

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {

                Spacer(modifier = Modifier.width(10.dp))

                WhiskeyDetailDropDownMenuComponent(
                    value = mainViewModel.currentMyReviewDayFilter.value,
                    onValueChange = {
                        mainViewModel.updateMyReviewFilter(it)
                        mainViewModel.getMyReviewList()
                    },
                    dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.day,
                    toggleDropDownMenuOption = {
                        mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                            MyReviewFilterItems.DAY
                        )
                    },
                    menuItems = listOf(MyReviewFilterItems.New, MyReviewFilterItems.Old)
                )

                Spacer(modifier = Modifier.width(10.dp))

                WhiskeyDetailDropDownMenuComponent(
                    value = mainViewModel.currentMyReviewTypeFilter.value,
                    onValueChange = { mainViewModel.updateMyWhiskeyFilter(it) },
                    dropDownMenuOption = mainViewModel.myReviewFilterDropDownMenuState.value.reviewType,
                    toggleDropDownMenuOption = {
                        mainViewModel.toggleMyWhiskeyReviewDropDownMenuState(
                            MyReviewFilterItems.REVIEW_TYPE
                        )
                    },
                    menuItems = listOf(
                        MyReviewFilterItems.Review,
                        MyReviewFilterItems.Graph,
                        MyReviewFilterItems.Detail
                    )
                )
            }

            if(mainViewModel.smallProgressIndicatorState.value){
                SmallSizeProgressIndicator(modifier = Modifier
                    .size(50.dp)
                    .padding(top = 30.dp))

            }else{
                when (mainViewModel.currentMyReviewTypeFilter.value) {
                    MyReviewFilterItems.Graph -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (mainViewModel.myReviewDataList.value.isEmpty()) {
                                EmptyReviewDataComponent(
                                    text = "리뷰가 존재하지 않습니다.",
                                    icon = ImageVector.vectorResource(R.drawable.graph)
                                )
                            } else {
                                Column(
                                    modifier = Modifier.height(330.dp).padding(start=5.dp,end=5.dp)
                                ) {
                                    MyReviewGraphComponent2(mainViewModel.myReviewDataList.value)
                                }
                            }
                        }
                    }

                    MyReviewFilterItems.Review -> {
                        if (mainViewModel.myReviewDataList.value.isEmpty()) {
                            EmptyReviewDataComponent(
                                text = "리뷰가 존재하지 않습니다.",
                                icon = ImageVector.vectorResource(R.drawable.empty_bottle)
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .heightIn(max=400.dp)
                            ) {
                                MySingleReviewComponent(
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
                                        mainViewModel.toggleDeleteReviewConfirmDialog()
                                        mainViewModel.setSelectReviewData(it)
                                    },
                                    modifyReview = { whiskyReviewData ->
                                        writeReviewViewModel.synchronizationWhiskyData(
                                            whiskyReviewData,
                                            mainViewModel.selectWhiskyData.value.korea_name ?: mainViewModel.selectWhiskyData.value.english_name,
                                            uriList = ImageConverter.byteArrayListToCacheUriList(
                                                context=context,
                                                byteArrayList = whiskyReviewData.imageList,
                                                fileNameList = whiskyReviewData.image_names
                                            )
                                        )
                                        navController.navigate("${MainRoute.INSERT_REVIEW}/modify")
                                    },
                                )
                            }
                        }
                    }
                    MyReviewFilterItems.Detail -> {
                        MyWhiskyDetailInfoComponent(
                            selectWhiskyData=mainViewModel.selectWhiskyData.value
                        )
                    }
                    MyReviewFilterItems.New -> TODO()
                    MyReviewFilterItems.Old -> TODO()
                    MyReviewFilterItems.Best -> TODO()
                    MyReviewFilterItems.Worst -> TODO()

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