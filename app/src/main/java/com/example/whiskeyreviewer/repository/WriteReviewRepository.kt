package com.example.whiskeyreviewer.repository

import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.WriteReviewData
import java.io.File

interface WriteReviewRepository {

    fun reviewSave(imageFiles:List<File>?, reviewData: SubmitWhiskyData, callback: (ServerResponse<Any>?) -> Unit)

    fun reviewModify(imageFiles:List<File>?, reviewData: SubmitWhiskyData,callback: (ServerResponse<Any>?) -> Unit)

    fun getReview(reviewData:WriteReviewData,callback: (ServerResponse<Any>?) -> Unit)

    fun deleteReview(reviewData:WriteReviewData,callback: (ServerResponse<Any>?) -> Unit)

}