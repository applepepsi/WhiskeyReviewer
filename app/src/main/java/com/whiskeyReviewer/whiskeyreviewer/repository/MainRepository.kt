package com.whiskeyReviewer.whiskeyreviewer.repository


import androidx.paging.PagingData
import com.whiskeyReviewer.whiskeyreviewer.data.BackupCodeData
import com.whiskeyReviewer.whiskeyreviewer.data.CustomWhiskyData
import com.whiskeyReviewer.whiskeyreviewer.data.LiveSearchData
import com.whiskeyReviewer.whiskeyreviewer.data.ServerResponse
import com.whiskeyReviewer.whiskeyreviewer.data.SingleWhiskeyData
import com.whiskeyReviewer.whiskeyreviewer.data.TokenData
import com.whiskeyReviewer.whiskeyreviewer.data.WhiskyReviewData
import com.whiskeyReviewer.whiskeyreviewer.data.WhiskyName
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File

interface MainRepository{



    suspend fun register(device_id: String): ApiResult<ServerResponse<TokenData>>

    suspend fun getMyWhiskyList(
        name: String?,
        category: String?,
        date_order: String?,
//        name_order: String,
        score_order: String?,
        open_date_order: String?,
    ): ApiResult<ServerResponse<List<SingleWhiskeyData>>>

    suspend fun getMyReviewList(
        whiskyUuid: String,
        order: String,
    ): ApiResult<ServerResponse<List<WhiskyReviewData>>>

    suspend fun addWhiskyNameSearch(
        name: String,
        category: String?,
    ): ApiResult<ServerResponse<List<WhiskyName>>>

    suspend fun saveOrModifyCustomWhisky(
        image: File?,
        data: CustomWhiskyData,
        modify: Boolean,

    ): ApiResult<ServerResponse<SingleWhiskeyData?>>

    fun getReviewSearchList(
        searchWord: String?,
        detailSearchWord: String?,

        likeAsc: String?,
        scoreAsc: String?,
        createdAtAsc: String?,
    ): Flow<PagingData<WhiskyReviewData>>

    suspend fun deleteReview(
        reviewUuid: String,
    ): ApiResult<ServerResponse<Any>>

    suspend fun getImageList(
        singleWhiskeyData: WhiskyReviewData
    ): ApiResult<WhiskyReviewData>

    suspend fun getImage(singleWhiskeyData: SingleWhiskeyData): ApiResult<SingleWhiskeyData>

    suspend fun likeReview(
        reviewUuid: String,

    ): ApiResult<ServerResponse<Any>>

    suspend fun cancelLikeReview(
        reviewUuid: String,
    ): ApiResult<ServerResponse<Any>>

    suspend fun getBackupCode(
    ): ApiResult<ServerResponse<BackupCodeData>>

    suspend fun submitBackupCode(
        backupCodeData: BackupCodeData,

    ): ApiResult<ServerResponse<Any>>

    suspend fun deleteWhisky(
        whisky_uuid: String,
    ): ApiResult<ServerResponse<Any>>

    suspend fun getLiveSearchData(
        searchText: String,

    ): ApiResult<ServerResponse<List<LiveSearchData>>>
}