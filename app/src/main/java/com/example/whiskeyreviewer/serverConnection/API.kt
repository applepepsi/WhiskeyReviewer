package com.example.oneplusone.serverConnection



import com.example.nextclass.utils.ADD_CUSTOM_WHISKY
import com.example.nextclass.utils.ADD_WHISKY_NAME_SEARCH
import com.example.nextclass.utils.DELETE_REVIEW
import com.example.nextclass.utils.GET_REVIEW
import com.example.nextclass.utils.GET_WHISKY_LIST
import com.example.nextclass.utils.REGISTER
import com.example.nextclass.utils.REVIEW_MODIFY
import com.example.nextclass.utils.REVIEW_SAVE
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyName
import com.example.whiskeyreviewer.data.WriteReviewData
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Query("date_order") date_order: String,
        @Query("name_order") name_order: String,
        @Query("score_order") score_order: String
    ): Response<ServerResponse<Any>>

    @GET(ADD_WHISKY_NAME_SEARCH)
    suspend fun addWhiskyNameSearch(
        @Query("name") name: String,
        @Query("category") category: String?,
    ): Response<ServerResponse<List<WhiskyName>>>

    @Multipart
    @POST(REVIEW_SAVE)
    suspend fun reviewSave(
        @Part images: List<MultipartBody.Part>,
        //멀티파트로 전송할때는 객체를 보내지 못함 -> JSON 형태로 직렬화하여 RequestBody로 변환한 후 전송해야함
        @Part("data") writeReviewData: RequestBody
    ):Response<ServerResponse<Any>>


    @Multipart
    @PUT(REVIEW_MODIFY)
    suspend fun reviewModify(
        @Path("identifier") identifier:String,
        @Part images : MultipartBody.Part,
        @Part("data") writeReviewData: WriteReviewData
    ):Response<ServerResponse<Any>>


    @GET(GET_REVIEW)
    suspend fun getReview(@Path("identifier") identifier:String):Response<ServerResponse<Any>>


    @DELETE(DELETE_REVIEW)
    suspend fun deleteReview(@Path("identifier") identifier:String):Response<ServerResponse<Any>>


    @POST(REVIEW_SAVE)
    suspend fun whiskySave(
        @Body device_id: String,

    ):Response<ServerResponse<Any>>

    @Multipart
    @POST(ADD_CUSTOM_WHISKY)
    suspend fun customWhiskySave(
        @Part image: MultipartBody.Part?,
        @Part("data") data: RequestBody
    ):Response<ServerResponse<Any>>

}