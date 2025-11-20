package com.whiskeyReviewer.whiskeyreviewer.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.whiskeyReviewer.nextclass.utils.SUCCESS_CODE
import com.whiskeyReviewer.oneplusone.serverConnection.API
import com.whiskeyReviewer.whiskeyreviewer.data.BackupCodeData
import com.whiskeyReviewer.whiskeyreviewer.data.CustomWhiskyData
import com.whiskeyReviewer.whiskeyreviewer.data.ImageData
import com.whiskeyReviewer.whiskeyreviewer.data.LiveSearchData
import com.whiskeyReviewer.whiskeyreviewer.data.ServerResponse
import com.whiskeyReviewer.whiskeyreviewer.data.SingleWhiskeyData
import com.whiskeyReviewer.whiskeyreviewer.data.TokenData
import com.whiskeyReviewer.whiskeyreviewer.data.WhiskyReviewData
import com.whiskeyReviewer.whiskeyreviewer.data.WhiskyName
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiHandler
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


    override suspend fun register(device_id: String): ServerResponse<TokenData>? {

        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="로그인 토큰발급") {
                api.getToken(device_id)
            }
        }
    }

    override suspend fun getMyWhiskyList(
        name: String?,
        category: String?,
        date_order: String?,
        score_order: String?,
        open_date_order: String?
    ): ServerResponse<List<SingleWhiskeyData>>? = withContext(Dispatchers.IO) {

        val result = ApiHandler.makeApiCall(tag = "나의 위스키 목록 가져오기") {
            api.getMyWhiskys(
                name = name,
                category = category,
                date_order = date_order,
                score_order = score_order,
                open_date_order = open_date_order,
            )
        }

        val updatedServerResponse = result?.let { response ->
            val updatedList = response.data?.map { singleWhiskeyData ->

                getImage(singleWhiskeyData)
            } ?: emptyList()

            response.copy(data = updatedList)
        }

        return@withContext updatedServerResponse
    }



    override suspend fun getMyReviewList(
        whiskyUuid: String,
        order: String,
    ):ServerResponse<List<WhiskyReviewData>>? = withContext(Dispatchers.IO){
        Log.d("whiskyUuid",whiskyUuid)
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

        return@withContext updatedServerResponse
    }

    override suspend fun getImageList(singleWhiskeyData: WhiskyReviewData):WhiskyReviewData {
        if(singleWhiskeyData.image_names==null){

            return singleWhiskeyData
        }
        return withContext(Dispatchers.IO) {
            val imageList = mutableListOf<ImageData>()

            singleWhiskeyData.image_names.forEach{ singleImageUrl->

                val result=ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                    api.getImage(image_name = singleImageUrl)
                }

                if (result != null) {
                    imageList.add(ImageData(result.bytes(),isOldImage = true))
                } else {
                    imageList.add(ImageData(ByteArray(0),isOldImage = true))
                }

            }

            singleWhiskeyData.copy(imageList = imageList)
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
            if(result!=null){
                Log.d("이미지 가져오기 결과",result.toString())
                singleWhiskeyData.copy(
                    image = ImageData(image=result.bytes(),isOldImage = true)
                )
            }else{
                singleWhiskeyData
            }

        }
    }

    override suspend fun likeReview(reviewUuid: String):ServerResponse<Any>?{

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 추천") {

                api.likeReview(
                    reviewUuid=reviewUuid
                )
            }
        }
    }

    override suspend fun cancelLikeReview(reviewUuid: String):ServerResponse<Any>? {
        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 추천 취소") {
                api.cancelLikeReview(
                    reviewUuid=reviewUuid
                )
            }
        }
    }

    override suspend fun getBackupCode():ServerResponse<BackupCodeData>? {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="백업코드 발급") {
                api.getBackupCode()
            }
        }
    }

    override suspend fun submitBackupCode(backupCodeData: BackupCodeData):ServerResponse<Any>? {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="백업코드 제출") {
                api.submitBackupCode(backupCodeData)
            }
        }
    }

    override suspend fun deleteWhisky(whisky_uuid: String):ServerResponse<Any>? {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="위스키 제거") {
                api.deleteWhisky(whisky_uuid = whisky_uuid)
            }
        }
    }

    override suspend fun getLiveSearchData(searchText: String):ServerResponse<List<LiveSearchData>>? {
        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="라이브 서치 데이터 가져오기") { api.whiskyLiveSearch(searchText)}
        }
    }


    override suspend fun addWhiskyNameSearch(name: String,category:String?):ServerResponse<List<WhiskyName>>? {

        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="위스키 이름 가져오기") { api.addWhiskyNameSearch(name,category=category) }
        }
    }

    override suspend fun saveOrModifyCustomWhisky(
        image: File?,
        data: CustomWhiskyData,
        modify: Boolean,

    ):ServerResponse<SingleWhiskeyData?>? = withContext(Dispatchers.IO){


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
        val updatedServerResponse = if (modify) {
            result?.let { serverResponse ->
                serverResponse.data?.let { modifyData ->
                    serverResponse.copy(data = getImage(modifyData))
                } ?: serverResponse
            }
        } else {
            result
        }

        return@withContext updatedServerResponse
    }


    override fun getReviewSearchList(
        searchWord: String?,
        detailSearchWord:String?,

        likeAsc:String?,
        scoreAsc: String?,
        createdAtAsc: String?,
    ): Flow<PagingData<WhiskyReviewData>> {
        Log.d("searchWord",searchWord ?: "null")
        Log.d("detailSearchWord",detailSearchWord ?: "null")
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


    override suspend fun deleteReview(reviewUuid: String):ServerResponse<Any>? {
        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 제거") { api.deleteReview(reviewUuid=reviewUuid)}
        }
    }

    suspend fun postImage(image: File?):String? {
        val requestFile = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val convertImage = requestFile?.let { MultipartBody.Part.createFormData("image", image.name, it) }

        Log.d("컨버트 이미지", convertImage.toString())
        Log.d("컨버트 이미지", image?.name ?:"null")
        return if(convertImage!=null){
            val result = withContext(Dispatchers.IO) {
                ApiHandler.makeApiCall(tag = "이미지 전송") {
                    api.imageUpload(image = convertImage)
                }
            }

            if (result != null && result.code == SUCCESS_CODE) {
                result.data
            } else {
                null
            }
        }else{
            null
        }

    }




}
