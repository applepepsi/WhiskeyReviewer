package com.example.oneplusone.serverConnection



import com.example.nextclass.utils.ADD_CUSTOM_WHISKY
import com.example.nextclass.utils.ADD_WHISKY_NAME_SEARCH
import com.example.nextclass.utils.DELETE_REVIEW
import com.example.nextclass.utils.BACKUP_CODE
import com.example.nextclass.utils.DELETE_WHISKY
import com.example.nextclass.utils.GET_IMAGE
import com.example.nextclass.utils.GET_REVIEW
import com.example.nextclass.utils.GET_REVIEW_SEARCH_LIST
import com.example.nextclass.utils.GET_WHISKY_LIST
import com.example.nextclass.utils.IMAGE_UPLOAD
import com.example.nextclass.utils.LIKE_REVIEW
import com.example.nextclass.utils.MODIFY_WHISKY
import com.example.nextclass.utils.REGISTER
import com.example.nextclass.utils.REVIEW_MODIFY
import com.example.nextclass.utils.REVIEW_SAVE
import com.example.whiskeyreviewer.data.BackupCodeData
import com.example.whiskeyreviewer.data.CustomWhiskyData
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.SingleWhiskeyData
import com.example.whiskeyreviewer.data.SubmitWhiskyData
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyReviewData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.pagingResponse.PagingResponse
import okhttp3.MultipartBody
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
//        @Query("name_order") name_order: String?,
        @Query("score_order") score_order: String?,
        @Query("open_date_order") open_date_order: String?
    ): Response<ServerResponse<List<SingleWhiskeyData>>>

    @GET(ADD_WHISKY_NAME_SEARCH)
    suspend fun addWhiskyNameSearch(
        @Query("name") name: String,
        @Query("category") category: String?,
    ): Response<ServerResponse<List<WhiskyName>>>

    @DELETE(DELETE_WHISKY)
    suspend fun deleteWhisky(
        @Path("whisky_uuid") whisky_uuid: String,
    ): Response<ServerResponse<Any>>


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

    @POST(LIKE_REVIEW)
    suspend fun likeReview(
        @Path("reviewUuid") reviewUuid: String,
    ): Response<ServerResponse<Any>>

    @DELETE(LIKE_REVIEW)
    suspend fun cancelLikeReview(
        @Path("reviewUuid") reviewUuid: String,
    ): Response<ServerResponse<Any>>

    @POST(REVIEW_SAVE)
    suspend fun whiskySave(
        @Body device_id: String,

    ):Response<ServerResponse<Any>>


    @POST(ADD_CUSTOM_WHISKY)
    suspend fun customWhiskySave(
        @Body data: CustomWhiskyData
    ):Response<ServerResponse<SingleWhiskeyData?>>

    @PUT(MODIFY_WHISKY)
    suspend fun modifyWhisky(
        @Path("myWhiskyUuid") myWhiskyUuid: String,
        @Body data: CustomWhiskyData,
    ):Response<ServerResponse<SingleWhiskeyData?>>


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

    @GET(GET_REVIEW_SEARCH_LIST)
    suspend fun getSearchReviewList(
        @Query("main_search_word") main_search_word: String?,
        @Query("sub_search_word") sub_search_word: String?,
        @Query("like_order") like_order: String?,
        @Query("score_order") score_order: String?,
        @Query("name_order") name_order: String?,
        @Query("create_order") create_order: String?,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ):Response<ServerResponse<PagingResponse>>


    @GET(BACKUP_CODE)
    suspend fun getBackupCode(
    ): Response<ServerResponse<BackupCodeData>>

    @POST(BACKUP_CODE)
    suspend fun submitBackupCode(
        @Body code: BackupCodeData
    ): Response<ServerResponse<Any>>
}