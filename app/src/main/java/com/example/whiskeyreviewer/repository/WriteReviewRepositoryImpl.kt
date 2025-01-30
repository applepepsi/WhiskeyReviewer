package com.example.whiskeyreviewer.repository

import android.util.Log
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.WriteReviewData
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
            Log.d("게시물 내용", buffer.readUtf8())
            Log.d("게시물 내용2", reviewData.toString())
            val result = try {
                val response = api.reviewSave(imageParts,reviewRequestBody)
                Log.d("게시물 전송 결과", response.toString())
                if (response.isSuccessful){
                    Log.d("게시물 전송 성공", response.body().toString())
                    response.body()
                } else {
                    Log.d("게시물 전송 실패","게시물 전송 실패")
                    null
                }

            } catch (e: IOException) {
                Log.e("연결 실패", "Network Error: ${e.message}", e)
                null
            } catch (e: Exception) {
                Log.e("오류 발생", "Unexpected Error: ${e.message}", e)
                null
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun reviewModify(
        reviewData: WriteReviewData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        TODO("Not yet implemented")
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
