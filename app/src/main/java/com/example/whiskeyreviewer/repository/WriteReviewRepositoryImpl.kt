package com.example.whiskeyreviewer.repository

import android.util.Log
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
import java.io.IOException
import javax.inject.Inject

class WriteReviewRepositoryImpl @Inject constructor(
    private val api: API
) : WriteReviewRepository {

    override fun reviewSave(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData, callback: (ServerResponse<Any>?) -> Unit) {


        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="리뷰 작성") {

                //이미지를 멀티파트바디로 변환
                val imageParts = imageFiles?.map { file ->
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                } ?: emptyList()

                // 리뷰 데이터 json으로 변환
                val json = Gson().toJson(reviewData)
                val reviewRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
                val buffer = Buffer()
                reviewRequestBody.writeTo(buffer)

                api.reviewSave(
                    imageParts,
                    reviewRequestBody
                ) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }

    }

    override fun reviewModify(
        imageFiles:List<File>?,
        reviewData: SubmitWhiskyData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="리뷰 수정") {

                //이미지를 멀티파트바디로 변환
                val imageParts = imageFiles?.map { file ->
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                } ?: emptyList()

                // 리뷰 데이터 json으로 변환
                val json = Gson().toJson(reviewData)
                val reviewRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
                val buffer = Buffer()
                reviewRequestBody.writeTo(buffer)

                api.reviewModify(
                    imageParts,
                    reviewRequestBody
                ) }
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

}
