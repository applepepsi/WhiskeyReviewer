package com.example.oneplusone.serverConnection



import com.example.nextclass.utils.ADD_CUSTOM_WHISKY
import com.example.nextclass.utils.ADD_WHISKY_NAME_SEARCH
import com.example.nextclass.utils.DELETE_REVIEW
import com.example.nextclass.utils.GET_IMAGE
import com.example.nextclass.utils.GET_REVIEW
import com.example.nextclass.utils.GET_WHISKY_LIST
import com.example.nextclass.utils.IMAGE_UPLOAD
import com.example.nextclass.utils.MODIFY_WHISKY
import com.example.nextclass.utils.REGISTER
import com.example.nextclass.utils.REVIEW_MODIFY
import com.example.nextclass.utils.REVIEW_SAVE
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface API {



    @POST(REGISTER)
    suspend fun getToken(@Body device_id: String): Response<ServerResponse<TokenData>>

    @GET(GET_WHISKY_LIST)
    suspend fun getMyWhiskys(
        @Query("name") name: String?,
        @Query("category") category: String?,
        @Query("date_order") date_order: String?,
        @Query("name_order") name_order: String?,
        @Query("score_order") score_order: String?
    ): Response<ServerResponse<List<SingleWhiskeyData>>>

    @GET(ADD_WHISKY_NAME_SEARCH)
    suspend fun addWhiskyNameSearch(
        @Query("name") name: String,
        @Query("category") category: String?,
    ): Response<ServerResponse<List<WhiskyName>>>


    @POST(REVIEW_SAVE)
    suspend fun reviewSave(
        //멀티파트로 전송할때는 객체를 보내지 못함 -> JSON 형태로 직렬화하여 RequestBody로 변환한 후 전송해야함
        @Body writeReviewData: SubmitWhiskyData
    ):Response<ServerResponse<Any>>



    @PUT(REVIEW_MODIFY)
    suspend fun reviewModify(
        @Path("reviewUuid") reviewUuid: String,
        @Body writeReviewData: SubmitWhiskyData

    ):Response<ServerResponse<Any>>


    @GET(GET_REVIEW)
    suspend fun getReview(
        @Path("myWhiskyUuid") myWhiskyUuid: String,
        @Query("order") order: String
    ): Response<ServerResponse<List<WhiskyReviewData>>>

    @POST(REVIEW_SAVE)
    suspend fun whiskySave(
        @Body device_id: String,

    ):Response<ServerResponse<Any>>


    @POST(ADD_CUSTOM_WHISKY)
    suspend fun customWhiskySave(
        @Body data: CustomWhiskyData
    ):Response<ServerResponse<Any>>

    @PUT(MODIFY_WHISKY)
    suspend fun modifyWhisky(
        @Path("myWhiskyUuid") myWhiskyUuid: String,
        @Body data: CustomWhiskyData,
    ):Response<ServerResponse<Any>>


    @Multipart
    @POST(IMAGE_UPLOAD)
    suspend fun imageUpload(
        @Part image: MultipartBody.Part?,
    ):Response<ServerResponse<String>>


    @GET(GET_IMAGE)
    suspend fun getImage(
        @Path("image_name") image_name: String,
    ):Response<ResponseBody>


    @DELETE(DELETE_REVIEW)
    suspend fun deleteReview(
        @Path("reviewUuid") reviewUuid: String,
    ):Response<ServerResponse<Any>>
}