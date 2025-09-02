package com.whiskeyReviewer.whiskeyreviewer.repository

import com.whiskeyReviewer.whiskeyreviewer.data.ServerResponse
import com.whiskeyReviewer.whiskeyreviewer.data.SubmitWhiskyData
import com.whiskeyReviewer.whiskeyreviewer.data.WriteReviewData
import java.io.File

interface WriteReviewRepository {

    fun reviewSave(imageFiles:List<File>?, reviewData: SubmitWhiskyData, callback: (ServerResponse<Any>?) -> Unit)

    fun reviewModify(imageFiles:List<File>?, reviewData: SubmitWhiskyData,callback: (ServerResponse<Any>?) -> Unit)

    fun getReview(reviewData:WriteReviewData,callback: (ServerResponse<Any>?) -> Unit)

    fun deleteReview(reviewData:WriteReviewData,callback: (ServerResponse<Any>?) -> Unit)

}