package com.example.whiskeyreviewer.repository

import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
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
        name: String,
        category: String,
        date_order: String,
        name_order: String,
        score_order: String,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="나의 위스키 목록 가져오기") {
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

    override fun addWhiskyNameSearch(name: String, callback: (ServerResponse<List<WhiskyName>>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="위스키 이름 가져오기") { api.addWhiskyNameSearch(name) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun addCustomWhisky(
        image: File?,
        data: CustomWhiskyData,
        callback: (ServerResponse<Any>?) -> Unit
    ) {

        val requestFile = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val convertImage = requestFile?.let { MultipartBody.Part.createFormData("image", image.name, it) }


        // 리뷰 데이터 json으로 변환
        val json = Gson().toJson(data)
        val reviewRequestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="커스텀 위스키 추가") { api.customWhiskySave(image = convertImage,data=reviewRequestBody) }
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
}
