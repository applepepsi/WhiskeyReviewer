package com.example.whiskeyreviewer.repository

import android.util.Log
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.utils.ApiHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

                api.getMyWhiskys(
                name=name,
                category=category,
                date_order=date_order,
                name_order=name_order,
                score_order=score_order,
            ) }

            val updatedServerResponse=result?.let{
                val whiskyDataList = mutableListOf<SingleWhiskeyData>()

                result.data?.forEach { singleWhiskeyData ->
                    val updatedData = getImage(singleWhiskeyData)
                    whiskyDataList.add(updatedData)
                }
                result.copy(data = whiskyDataList)
            }

            withContext(Dispatchers.Main) {
                callback(updatedServerResponse)
            }
        }
    }

    private suspend fun getImage(singleWhiskeyData: SingleWhiskeyData):SingleWhiskeyData {
        if(singleWhiskeyData.image_name==null){

            return singleWhiskeyData
        }
        return withContext(Dispatchers.IO) {
            val result = ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                api.getImage(image_name = singleWhiskeyData.image_name)
            }
            singleWhiskeyData.copy(image = result?.bytes())
        }
    }



    override fun getMyReviewList(
        whiskyUuid: String,

        order: String,
        callback: (ServerResponse<List<WhiskyReviewData>>?) -> Unit
    ){
        Log.d("whiskyUuid",whiskyUuid)
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="나의 리뷰 가져오기") {

                api.getReview(
                    myWhiskyUuid=whiskyUuid,
                    order=order
                )
            }

            val updatedServerResponse=result?.let{
                val whiskyDataList = mutableListOf<WhiskyReviewData>()

                result.data?.forEach { singleReviewData->
                    val updatedData = getImageList(singleReviewData)
                    whiskyDataList.add(updatedData)
                }
                result.copy(data = whiskyDataList)
            }

            withContext(Dispatchers.Main) {
                callback(updatedServerResponse)
            }
        }
    }

    private suspend fun getImageList(singleWhiskeyData: WhiskyReviewData):WhiskyReviewData {
        if(singleWhiskeyData.image_url==null){

            return singleWhiskeyData
        }
        return withContext(Dispatchers.IO) {
            val imageList = mutableListOf<ByteArray>()

            singleWhiskeyData.image_url.forEach{singleImageUrl->

                val result=ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                    api.getImage(image_name = singleImageUrl)
                }
                result?.let{
                    imageList.add(result.bytes())
                }
            }

            singleWhiskeyData.copy(imageList = imageList)
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




}
