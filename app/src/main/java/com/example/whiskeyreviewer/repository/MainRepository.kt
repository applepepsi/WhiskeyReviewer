package com.example.whiskeyreviewer.repository


import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskeyFilterItems
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import java.io.File

interface MainRepository{



    fun register(device_id:String,callback: (ServerResponse<TokenData>?) -> Unit)

    fun getMyWhiskyList(
        name:String,
        category: String,
        date_order: String,
        name_order:String,
        score_order:String,
        callback: (ServerResponse<Any>?) -> Unit
    )

    fun addWhiskyNameSearch(
        name:String,
        callback: (ServerResponse<List<WhiskyName>>?) -> Unit
    )


    fun addCustomWhisky(
        image:File?,
        data:CustomWhiskyData,
        callback: (ServerResponse<Any>?) -> Unit
    )

    fun getWhiskyList(
        name:String,
        category: String,
        date_order: String,
        name_order:String,
        score_order:String,
        callback: (ServerResponse<Any>?) -> Unit
    )

}