package com.example.whiskeyreviewer.repository


import androidx.paging.PagingData
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MainRepository{



    fun register(device_id:String,callback: (ServerResponse<TokenData>?) -> Unit)

    fun getMyWhiskyList(
        name:String?,
        category: String?,
        date_order: String?,
//        name_order:String,
        score_order:String?,
        callback: (ServerResponse<List<SingleWhiskeyData>>?) -> Unit
    )

    fun getMyReviewList(
        whiskyUuid:String,
        order:String,
        callback: (ServerResponse<List<WhiskyReviewData>>?) -> Unit
    )

    fun addWhiskyNameSearch(
        name:String,
        category: String?,
        callback: (ServerResponse<List<WhiskyName>>?) -> Unit
    )


    fun saveOrModifyCustomWhisky(
        image:File?,
        data:CustomWhiskyData,
        modify:Boolean,
        callback: (ServerResponse<Any>?) -> Unit
    )

    fun getReviewSearchList(
        searchWord:String?,
        detailSearchWord:String?,

        likeAsc:String?,
        scoreAsc:String?,
        createdAtAsc: String?,
    ): Flow<PagingData<WhiskyReviewData>>

    fun deleteReview(
        reviewUuid:String,
        callback: (ServerResponse<Any>?) -> Unit
    )

    suspend fun getImageList(
        singleWhiskeyData: WhiskyReviewData
    ):WhiskyReviewData

    suspend fun getImage(singleWhiskeyData: SingleWhiskeyData):SingleWhiskeyData

    fun likeReview(
        reviewUuid: String,
        callback: (ServerResponse<Any>?) -> Unit
    )

    fun cancelLikeReview(
        reviewUuid: String,
        callback: (ServerResponse<Any>?) -> Unit
    )


}