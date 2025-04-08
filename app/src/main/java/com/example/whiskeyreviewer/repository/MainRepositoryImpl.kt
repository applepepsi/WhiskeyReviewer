package com.example.whiskeyreviewer.repository

import android.media.Image
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nextclass.utils.SUCCESS_CODE
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.BackupCodeData
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ImageData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.utils.ApiHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
        date_order: String?,
//        name_order: String,
        score_order: String?,
        callback: (ServerResponse<List<SingleWhiskeyData>>?) -> Unit
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="나의 위스키 목록 가져오기") {

                //이름순은 제거 예정 임시로 asc
                api.getMyWhiskys(
                name=name,
                category=category,
                date_order=date_order,
                name_order="asc",
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

    override suspend fun getImage(singleWhiskeyData: SingleWhiskeyData):SingleWhiskeyData {
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

    override suspend fun getImageList(singleWhiskeyData: WhiskyReviewData):WhiskyReviewData {
        if(singleWhiskeyData.image_names==null){

            return singleWhiskeyData
        }
        return withContext(Dispatchers.IO) {
            val imageList = mutableListOf<ByteArray>()

            singleWhiskeyData.image_names.forEach{ singleImageUrl->

                val result=ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                    api.getImage(image_name = singleImageUrl)
                }

                if (result != null) {
                    imageList.add(result.bytes())
                } else {
                    imageList.add(ByteArray(0))
                }

            }

            singleWhiskeyData.copy(imageList = imageList)
        }
    }

    override fun likeReview(reviewUuid: String,callback: (ServerResponse<Any>?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="리뷰 추천") {

                api.likeReview(
                    reviewUuid=reviewUuid
                )
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun cancelLikeReview(reviewUuid: String, callback: (ServerResponse<Any>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="리뷰 추천 취소") {
                api.cancelLikeReview(
                    reviewUuid=reviewUuid
                )
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getBackupCode(callback: (ServerResponse<BackupCodeData>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="백업코드 발급") {
                api.getBackupCode()
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun submitBackupCode(backupCodeData: BackupCodeData,callback: (ServerResponse<Any>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ApiHandler.makeApiCall(tag="백업코드 제출") {
                api.submitBackupCode(backupCodeData)
            }

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

    override fun saveOrModifyCustomWhisky(
        image: File?,
        data: CustomWhiskyData,
        modify: Boolean,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            val imageLink = image?.let {
                postImage(it).also { link ->
                    Log.d("이미지 링크", link ?: "null")
                }
            }


            val newData = data.copy(image_name = imageLink ?: data.image_name)

            val result = if (modify) {
                // 수정
                Log.d("수정 데이터", newData.toString())
                ApiHandler.makeApiCall(tag = "위스키 수정") {
                    api.modifyWhisky(myWhiskyUuid = data.whisky_uuid, data = newData)
                }
            } else {
                // 추가
                ApiHandler.makeApiCall(tag = "커스텀 위스키 추가") {
                    api.customWhiskySave(data = newData)
                }
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }


    override fun getReviewSearchList(
        searchWord: String?,
        detailSearchWord:String?,

        likeAsc:String?,
        scoreAsc: String?,
        createdAtAsc: String?,
    ): Flow<PagingData<WhiskyReviewData>> {
        Log.d("플로우",searchWord ?: "null")
        return Pager(PagingConfig(pageSize = 10)) {
            PagingSource(
                apiService = api,
                searchWord = searchWord,
                detailSearchWord = detailSearchWord,
                likeAsc = likeAsc,
                scoreAsc = scoreAsc,
                createdAtAsc = createdAtAsc
            )
        }.flow
    }


    override fun deleteReview(reviewUuid: String, callback: (ServerResponse<Any>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val result = ApiHandler.makeApiCall(tag="리뷰 제거") { api.deleteReview(reviewUuid=reviewUuid) }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
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
