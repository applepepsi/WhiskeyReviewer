package com.example.whiskeyreviewer.component.camea


import android.content.ContentValues
import android.content.Context

import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment

import android.util.Log
import androidx.camera.core.CameraSelector

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material.IconButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Matrix
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.whiskeyreviewer.R
import com.example.whiskeyreviewer.component.customComponent.CustomToast
import com.example.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.ui.theme.LightBlackColor
import com.example.whiskeyreviewer.ui.theme.MainColor
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun CameraComponent(
    mainViewModel: MainViewModel,
    writeReviewViewModel: WriteReviewViewModel,
    navController: NavHostController,
    tag:String
) {

    val context= LocalContext.current
    val scope = rememberCoroutineScope()
    val customToast = CustomToast(LocalContext.current)
    Log.d("태그",tag)
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

//    BackHandler {
//        onDismiss()
//    }

    if(mainViewModel.errorToastState.value) {
        customToast.MakeText(text = mainViewModel.errorToastMessage.value, icon = mainViewModel.errorToastIcon.value)
        mainViewModel.resetToastErrorState()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Box(
            modifier = Modifier.background(Color.Black).fillMaxWidth().padding(top=12.dp),

        ){
            CustomIconComponent(
                icon = Icons.Default.Camera,
                onClick = {
                    takePhoto(
                        context=context,
                        controller=controller,
                        onPhotoTaken = {uri->
//
//                            mainViewModel.addImage(uri)
                            if(uri!=null){
                                mainViewModel.setErrorToastMessage(
                                    icon=R.drawable.success_icon,
                                    text="이미지 저장에 성공했습니다."
                                )

                                when(tag){
                                    "insertReview"->{
                                        writeReviewViewModel.setSelectedImage(listOf(uri))
                                    }
                                    "addWhisky"->{
                                        mainViewModel.setSelectedImage(uri)
                                    }
                                    "changeWhiskyImage"->{

                                    }
                                }
                            }else{
                                mainViewModel.setErrorToastMessage(
                                    icon=R.drawable.fail_icon,
                                    text="이미지 저장에 실패했습니다."
                                )
                            }
                            navController.popBackStack()

                        }
                    )
                },
                modifier = Modifier.padding(bottom=15.dp).size(45.dp).align(Alignment.Center),
                iconSize = Modifier.size(37.dp),
                backGroundColor = Color.White,
                tint = LightBlackColor
            )

            CustomIconComponent(
                icon = Icons.Default.Cameraswitch,
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else CameraSelector.DEFAULT_BACK_CAMERA
                },
                modifier = Modifier.padding(end=10.dp,bottom=15.dp).size(45.dp).align(Alignment.CenterEnd),
                iconSize = Modifier.size(37.dp),
                backGroundColor = Color.White,
                tint = LightBlackColor
            )
        }

    }
}

private fun takePhoto(
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Uri?) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
//                val bitmap = image.toBitmap()


                //이미지 회전
                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )


                // 비트맵을 파일로 저장하고 uri 반환
                val uri = saveBitmapToFile(context, rotatedBitmap)

                onPhotoTaken(uri)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("카메라", "사진찍기 실패: ", exception)
            }
        }
    )
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {

    val fileName = "${System.currentTimeMillis()}.jpg"
    var outputStream: OutputStream? = null

    //원래 촬영은 가능하지만 갤러리에 이미지가 보이지 않는 문제가 있었음
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            context.contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                //새로운 방식: 모든 앱이 공유하는 저장공간(갤러리에 보임)
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }

                saveBitmapOutputStream(bitmap, outputStream)

                return imageUri
            }
        } else {

            //기존 방식: 앱파일만 저장하는 공간 갤러리에서 접근할 수 없음
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(imagesDir, fileName)

            outputStream = FileOutputStream(file)

            saveBitmapOutputStream(bitmap, outputStream)

            return Uri.fromFile(file)
        }
    } catch (e: Exception) {
        Log.e("카메라", "이미지 저장 실패: ", e)
        return null
    } finally {
        outputStream?.close()
    }
}


private fun saveBitmapOutputStream(bitmap: Bitmap, outputStream: OutputStream?) {
    outputStream?.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

