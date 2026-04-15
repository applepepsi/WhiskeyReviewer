package com.whiskeyReviewer.whiskeyreviewer.repository

import com.whiskeyReviewer.whiskeyreviewer.data.ServerResponse
import com.whiskeyReviewer.whiskeyreviewer.data.SubmitWhiskyData
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiResult
import java.io.File

interface WriteReviewRepository {

    suspend fun reviewSave(
        imageFiles: List<File>?,
        reviewData: SubmitWhiskyData
    ): ApiResult<ServerResponse<Any>>

    suspend fun reviewModify(
        imageFiles: List<File>?,
        reviewData: SubmitWhiskyData
    ): ApiResult<ServerResponse<Any>>
}