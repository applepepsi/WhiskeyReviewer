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
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiResult
import com.whiskeyReviewer.whiskeyreviewer.utils.map
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


    override suspend fun register(device_id: String): ApiResult<ServerResponse<TokenData>> {

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
    ): ApiResult<ServerResponse<List<SingleWhiskeyData>>> = withContext(Dispatchers.IO) {

        val result = ApiHandler.makeApiCall(tag = "나의 위스키 목록 가져오기") {
            api.getMyWhiskys(
                name = name,
                category = category,
                date_order = date_order,
                score_order = score_order,
                open_date_order = open_date_order,
            )
        }

        return@withContext result.map { response ->
            val updatedList = response.data?.map { singleWhiskeyData ->
                when (val imageResult = getImage(singleWhiskeyData)) {
                    is ApiResult.Success -> imageResult.data
                    else -> singleWhiskeyData
                }
            } ?: emptyList()

            response.copy(data = updatedList)
        }
    }



    override suspend fun getMyReviewList(
        whiskyUuid: String,
        order: String,
    ): ApiResult<ServerResponse<List<WhiskyReviewData>>> = withContext(Dispatchers.IO){
        Log.d("whiskyUuid",whiskyUuid)
        val result = ApiHandler.makeApiCall(tag="나의 리뷰 가져오기") {
            api.getReview(
                myWhiskyUuid=whiskyUuid,
                order=order
            )
        }
        return@withContext result
    }

    override suspend fun getImageList(singleWhiskeyData: WhiskyReviewData): ApiResult<WhiskyReviewData> {
        if (singleWhiskeyData.image_names == null) {
            return ApiResult.Success(singleWhiskeyData)
        }
        return withContext(Dispatchers.IO) {
            val imageList = mutableListOf<ImageData>()

            singleWhiskeyData.image_names.forEach { singleImageUrl ->

                val result = ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                    api.getImage(image_name = singleImageUrl)
                }

                when (result) {
                    is ApiResult.Success -> imageList.add(
                        ImageData(result.data.bytes(), isOldImage = true)
                    )
                    else -> imageList.add(ImageData(ByteArray(0), isOldImage = true))
                }

            }

            ApiResult.Success(singleWhiskeyData.copy(imageList = imageList))
        }
    }

    override suspend fun getImage(singleWhiskeyData: SingleWhiskeyData): ApiResult<SingleWhiskeyData> {
        if (singleWhiskeyData.image_name == null) {
            return ApiResult.Success(singleWhiskeyData)
        }
        return withContext(Dispatchers.IO) {
            val result = ApiHandler.makeApiCall(tag = "이미지 가져오기") {
                api.getImage(image_name = singleWhiskeyData.image_name)
            }
            when (result) {
                is ApiResult.Success -> {
                    Log.d("이미지 가져오기 결과", result.toString())
                    ApiResult.Success(
                        singleWhiskeyData.copy(
                            image = ImageData(image = result.data.bytes(), isOldImage = true)
                        )
                    )
                }
                else -> ApiResult.Success(singleWhiskeyData)
            }

        }
    }

    override suspend fun likeReview(reviewUuid: String): ApiResult<ServerResponse<Any>>{

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 추천") {

                api.likeReview(
                    reviewUuid=reviewUuid
                )
            }
        }
    }

    override suspend fun cancelLikeReview(reviewUuid: String): ApiResult<ServerResponse<Any>> {
        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 추천 취소") {
                api.cancelLikeReview(
                    reviewUuid=reviewUuid
                )
            }
        }
    }

    override suspend fun getBackupCode(): ApiResult<ServerResponse<BackupCodeData>> {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="백업코드 발급") {
                api.getBackupCode()
            }
        }
    }

    override suspend fun submitBackupCode(backupCodeData: BackupCodeData): ApiResult<ServerResponse<Any>> {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="백업코드 제출") {
                api.submitBackupCode(backupCodeData)
            }
        }
    }

    override suspend fun deleteWhisky(whisky_uuid: String): ApiResult<ServerResponse<Any>> {

        return  withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="위스키 제거") {
                api.deleteWhisky(whisky_uuid = whisky_uuid)
            }
        }
    }

    override suspend fun getLiveSearchData(searchText: String): ApiResult<ServerResponse<List<LiveSearchData>>> {
        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="라이브 서치 데이터 가져오기") { api.whiskyLiveSearch(searchText)}
        }
    }


    override suspend fun addWhiskyNameSearch(name: String,category:String?): ApiResult<ServerResponse<List<WhiskyName>>> {

        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="위스키 이름 가져오기") { api.addWhiskyNameSearch(name,category=category) }
        }
    }

    override suspend fun saveOrModifyCustomWhisky(
        image: File?,
        data: CustomWhiskyData,
        modify: Boolean,

    ): ApiResult<ServerResponse<SingleWhiskeyData?>> = withContext(Dispatchers.IO){


        val imageLinkResult = image?.let { postImage(it) } ?: ApiResult.Success(null)
        val imageLink = when (imageLinkResult) {
            is ApiResult.Success -> imageLinkResult.data
            is ApiResult.HttpError -> return@withContext ApiResult.HttpError(
                imageLinkResult.code,
                imageLinkResult.message,
                imageLinkResult.errorBody
            )
            is ApiResult.NetworkError -> return@withContext ApiResult.NetworkError(imageLinkResult.exception)
            is ApiResult.UnknownError -> return@withContext ApiResult.UnknownError(imageLinkResult.exception)
        }
        Log.d("이미지 링크", imageLink ?: "null")
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
            result.map { serverResponse ->
                serverResponse.data?.let { modifyData ->
                    when (val imageResult = getImage(modifyData)) {
                        is ApiResult.Success -> serverResponse.copy(data = imageResult.data)
                        else -> serverResponse
                    }
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


    override suspend fun deleteReview(reviewUuid: String): ApiResult<ServerResponse<Any>> {
        return withContext(Dispatchers.IO) {
            ApiHandler.makeApiCall(tag="리뷰 제거") { api.deleteReview(reviewUuid=reviewUuid)}
        }
    }

    suspend fun postImage(image: File?): ApiResult<String?> {
        val requestFile = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val convertImage = requestFile?.let { MultipartBody.Part.createFormData("image", image.name, it) }

        Log.d("컨버트 이미지", convertImage.toString())
        Log.d("컨버트 이미지", image?.name ?:"null")
        return if (convertImage != null) {
            val result = withContext(Dispatchers.IO) {
                ApiHandler.makeApiCall(tag = "이미지 전송") {
                    api.imageUpload(image = convertImage)
                }
            }

            when (result) {
                is ApiResult.Success -> {
                    if (result.data.code == SUCCESS_CODE) {
                        ApiResult.Success(result.data.data)
                    } else {
                        ApiResult.Success(null)
                    }
                }
                is ApiResult.HttpError -> ApiResult.HttpError(
                    result.code,
                    result.message,
                    result.errorBody
                )
                is ApiResult.NetworkError -> ApiResult.NetworkError(result.exception)
                is ApiResult.UnknownError -> ApiResult.UnknownError(result.exception)
            }
        } else {
            ApiResult.Success(null)
        }

    }




}
