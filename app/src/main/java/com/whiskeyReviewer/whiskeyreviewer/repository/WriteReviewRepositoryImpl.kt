package com.whiskeyReviewer.whiskeyreviewer.repository

import android.util.Log
import com.whiskeyReviewer.nextclass.utils.SUCCESS_CODE
import com.whiskeyReviewer.oneplusone.serverConnection.API
import com.whiskeyReviewer.whiskeyreviewer.data.ServerResponse
import com.whiskeyReviewer.whiskeyreviewer.data.SubmitWhiskyData
import com.whiskeyReviewer.whiskeyreviewer.data.WriteReviewData
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class WriteReviewRepositoryImpl @Inject constructor(
    private val api: API
) : WriteReviewRepository {

    override suspend fun reviewSave(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData):ServerResponse<Any>? = withContext(Dispatchers.IO){

        Log.d("이미지 데이터들", imageFiles.toString())
        val imageLinks = imageFiles?.map { singleImage ->
            postImage(singleImage)
        }
        val newData = reviewData.copy(image_names = imageLinks)
        val result = ApiHandler.makeApiCall(tag = "리뷰 추가") { api.reviewSave(newData) }

        return@withContext result


    }


    //todo 리뷰 수정할때 포스트 이미지는 추가된 이미지만 전송, 수정된 리뷰를 보낼 때는 기존 이미지 주소 까지 같이 전송
    override suspend fun reviewModify(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData,

    ):ServerResponse<Any>? {

        Log.d("이미지 데이터들", imageFiles.toString())
        return withContext(Dispatchers.IO){
            ApiHandler.makeApiCall(tag="리뷰 수정") {
                Log.d("수정 데이터", reviewData.toString())
                val newUploadedLinks = imageFiles?.map { singleImage ->
                    postImage(singleImage)
                }

                val oldImageList = reviewData.image_names ?: emptyList()

                var newImageLinks: List<String?>?=null

                val finalImageLinks = if (newUploadedLinks != null) {
                    oldImageList + newUploadedLinks
                } else {
                    oldImageList
                }
                val newData = reviewData.copy(
                    image_names = if (finalImageLinks.isNotEmpty()) finalImageLinks else null
                )
                Log.d("수정 이미지 결과", newImageLinks.toString())

                api.reviewModify(reviewUuid = newData.my_whisky_uuid, writeReviewData = newData)
            }
        }

    }


    suspend fun postImage(image: File?):String? {
        val requestFile = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val convertImage = requestFile?.let { MultipartBody.Part.createFormData("image", image.name, it) }

        Log.d("변환 이미지 이름", image.toString())
        val result = withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag = "이미지 전송") {
                api.imageUpload(image = convertImage)
            }
        }

        return if (result != null && result.code == SUCCESS_CODE) {
            result.data
        } else {
            null
        }
    }

}
