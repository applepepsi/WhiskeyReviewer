package com.example.whiskeyreviewer.repository

import android.util.Log
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.WriteReviewData
import com.example.whiskeyreviewer.utils.ApiHandler
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.io.File
import javax.inject.Inject

class WriteReviewRepositoryImpl @Inject constructor(
    private val api: API
) : WriteReviewRepository {

    override fun reviewSave(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData, callback: (ServerResponse<Any>?) -> Unit) {


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("이미지 데이터들", imageFiles.toString())
            val imageLinks = imageFiles?.map { singleImage ->
                postImage(singleImage)
            }
            val newData = reviewData.copy(image_names = imageLinks)

            Log.d("리뷰 데이터", newData.toString())
//            api.reviewSave(
//
//                newData
//            )

            val result = ApiHandler.makeApiCall(tag = "리뷰 추가") { api.reviewSave(newData) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }


//        CoroutineScope(Dispatchers.IO).launch {
//            val result = ApiHandler.makeApiCall(tag="리뷰 작성") {
//
//                //이미지를 멀티파트바디로 변환
//                val imageParts = imageFiles?.map { file ->
//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    MultipartBody.Part.createFormData("images", file.name, requestFile)
//                } ?: emptyList()
//
//                // 리뷰 데이터 json으로 변환
//                val json = Gson().toJson(reviewData)
//                val reviewRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
//                val buffer = Buffer()
//                reviewRequestBody.writeTo(buffer)
//
//
//
//                api.reviewSave(
//
//                    reviewRequestBody
//                ) }
//            withContext(Dispatchers.Main) {
//                callback(result)
//            }
//        }

    }


    //todo 리뷰 수정할때 포스트 이미지는 추가된 이미지만 전송, 수정된 리뷰를 보낼 때는 기존 이미지 주소 까지 같이 전송
    override fun reviewModify(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("이미지 데이터들", imageFiles.toString())
            val result = ApiHandler.makeApiCall(tag="리뷰 수정") {
                Log.d("수정 데이터", reviewData.toString())
                val imageLinks = imageFiles?.map { singleImage ->
                    postImage(singleImage)
                }


                val oldImageList=reviewData.image_names

                var newImageLinks: List<String?>?=null

                if(imageLinks!=null){
                    newImageLinks = if(oldImageList!=null){
                        oldImageList+imageLinks
                    }else{
                        imageLinks
                    }
                }
                val newData = reviewData.copy(image_names = newImageLinks)
                Log.d("수정 이미지 결과", newImageLinks.toString())

                api.reviewModify(reviewUuid = newData.my_whisky_uuid, writeReviewData = newData) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getReview(reviewData: WriteReviewData, callback: (ServerResponse<Any>?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteReview(
        reviewData: WriteReviewData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        TODO("Not yet implemented")
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
