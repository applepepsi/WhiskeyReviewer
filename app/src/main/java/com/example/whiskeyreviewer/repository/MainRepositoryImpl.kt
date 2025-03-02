package com.example.whiskeyreviewer.repository

import android.media.Image
import android.util.Log
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyName
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
import okhttp3.ResponseBody
import okio.Buffer
import java.io.File
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: API
) : MainRepository {


    override fun register(device_id: String, callback: (ServerResponse<TokenData>?) -> Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val result = try {
//                val response = api.getToken(device_id)
//
//                if (response.isSuccessful){
//                    Log.d("토큰 발급 성공", response.body().toString())
//                    response.body()
//                } else {
//                    Log.d("토큰 발급 실패","토큰 발급 실패")
//                    null
//                }
//
//            } catch (e: IOException) {
//                Log.e("연결 실패", "Network Error: ${e.message}", e)
//                null
//            } catch (e: Exception) {
//                Log.e("오류 발생", "Unexpected Error: ${e.message}", e)
//                null
//            }
//            withContext(Dispatchers.Main) {
//                callback(result)
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="로그인 토큰발급") { api.getToken(device_id) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getMyWhiskyList(
        name: String?,
        category: String?,
        date_order: String,
        name_order: String,
        score_order: String,
        callback: (ServerResponse<List<SingleWhiskeyData>>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="나의 위스키 목록 가져오기") {

                Log.d("WhiskyList", "이름: ${name ?: "없음"}")
                Log.d("WhiskyList", "카테고리: ${category ?: "없음"}")
                Log.d("WhiskyList", "날짜 정렬: $date_order")
                Log.d("WhiskyList", "이름 정렬: $name_order")
                Log.d("WhiskyList", "점수 정렬: $score_order")

                api.getMyWhiskys(
                name=name,
                category=category,
                date_order=date_order,
                name_order=name_order,
                score_order=score_order,
            ) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getMyReviewList(
        whiskyUuid: String,
        bottleNumber: Int,
        order: String,
        callback: (ServerResponse<Any>?) -> Unit
    ){

        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="나의 리뷰 가져오기") {

                api.getReview(
                    uuid=whiskyUuid,
                    bottleNumber=bottleNumber,
                    order=order
                ) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun addWhiskyNameSearch(name: String,category:String?, callback: (ServerResponse<List<WhiskyName>>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            if (category != null) {
                Log.d("카테고리",category)
            }
            val result = ApiHandler.makeApiCall(tag="위스키 이름 가져오기") { api.addWhiskyNameSearch(name,category=category) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun addCustomWhisky(
        image:File?,
        data: CustomWhiskyData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {


        CoroutineScope(Dispatchers.IO).launch {

            val imageLink = image?.let {
                postImage(it).also { link ->
                    Log.d("이미지 링크", link ?: "null")
                }
            }
            val newData=data.copy(image_name = imageLink)

            val result = ApiHandler.makeApiCall(tag="커스텀 위스키 추가") { api.customWhiskySave(data=newData) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }



    override fun getWhiskyList(
        name: String,
        category: String,
        date_order: String,
        name_order: String,
        score_order: String,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    suspend fun postImage(image: File?):String? {
        val requestFile = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val convertImage = requestFile?.let { MultipartBody.Part.createFormData("image", image.name, it) }


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

    override fun getImage(image_name: String, callback: (ResponseBody?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val result = ApiHandler.makeApiCall(tag="이미지 가져오기") { api.getImage(image_name=image_name) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }


}
