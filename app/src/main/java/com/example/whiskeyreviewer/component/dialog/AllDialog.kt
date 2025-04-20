package com.example.whiskeyreviewer.component.dialog

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.camera.CameraDialog
import com.example.whiskeyreviewer.component.customComponent.ProgressIndicatorDialog
import com.example.whiskeyreviewer.component.permission.PermissionRationale
import com.example.whiskeyreviewer.component.permission.rememberPermissionList
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AllDialogs(
    mainViewModel: MainViewModel,
    writeReviewViewModel: WriteReviewViewModel,
    mainNavController: NavHostController
) {


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

    ImageViewerDialog(
        currentImage = mainViewModel.selectImageUrl.value,
        toggleOption = { mainViewModel.toggleImageDialogState() },
        currentState = mainViewModel.imageDialogState.value
    )
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                mainViewModel.setSelectedImage(uri)
            }
        }
    )

    val multiPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 3
        ),
        onResult = { uris ->
            writeReviewViewModel.setSelectedImage(uris)
        }
    )
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    SingleImageTypeSelectDialog(
        albumSelectState = mainViewModel.imageTypeSelectState.value.albumSelected,
        cameraSelectState = mainViewModel.imageTypeSelectState.value.cameraSelected,
        confirm = {
            when {
                mainViewModel.imageTypeSelectState.value.albumSelected -> {

                    singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }
                mainViewModel.imageTypeSelectState.value.cameraSelected -> {

                    when {
                        cameraPermissionState.status.isGranted-> {
                            mainViewModel.setCameraTag(tag= AddImageTag.ChangeWhiskyImage)
                            mainViewModel.toggleCameraState(state = true)
                        }
                        else -> {
                            if (cameraPermissionState.status.shouldShowRationale) {
                                cameraPermissionState.launchPermissionRequest()
                            } else {

                                mainViewModel.setErrorToastMessage(
                                    icon=R.drawable.fail_icon,
                                    text="카메라 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요."
                                )
                            }

                        }
                    }

                }

                else -> {}
            }.also {
                mainViewModel.toggleSingleImageTypeSelectDialogState()
            }
        },
        onSelect = { mainViewModel.updateSelectImageType(it) },
        toggleOption = {mainViewModel.toggleSingleImageTypeSelectDialogState()},
        currentState = mainViewModel.singleImageTypeSelectDialogState.value
    )

    MultiImageTypeSelectDialog(
        albumSelectState = mainViewModel.imageTypeSelectState.value.albumSelected,
        cameraSelectState = mainViewModel.imageTypeSelectState.value.cameraSelected,
        confirm = {
            when {
                mainViewModel.imageTypeSelectState.value.albumSelected -> {

                    multiPhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                mainViewModel.imageTypeSelectState.value.cameraSelected -> {
                    when {
                        cameraPermissionState.status.isGranted-> {
                            mainViewModel.setCameraTag(tag= AddImageTag.InsertReview)
                            mainViewModel.toggleCameraState(state = true)
                        }
                        else -> {
                            if (cameraPermissionState.status.shouldShowRationale) {
                                cameraPermissionState.launchPermissionRequest()
                            } else {
                                mainViewModel.setErrorToastMessage(
                                    icon=R.drawable.fail_icon,
                                    text="카메라 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요."
                                )
                            }

                        }
                    }
                }

                else -> {}
            }.also {
                mainViewModel.toggleMultiImageTypeSelectDialogState()
            }
        },
        onSelect = { mainViewModel.updateSelectImageType(it) },
        toggleOption = {mainViewModel.toggleMultiImageTypeSelectDialogState()},
        currentState = mainViewModel.multiImageTypeSelectDialogState.value
    )

}