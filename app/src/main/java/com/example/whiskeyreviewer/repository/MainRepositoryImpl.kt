package com.example.whiskeyreviewer.repository

import android.util.Log
import com.example.oneplusone.serverConnection.API
import com.example.whiskeyreviewer.data.ServerResponse
import com.example.whiskeyreviewer.data.TokenData
import com.example.whiskeyreviewer.data.WhiskyName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: API
) : MainRepository {


    override fun register(device_id: String, callback: (ServerResponse<TokenData>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val result = try {
                val response = api.getToken(device_id)

                if (response.isSuccessful){
                    Log.d("댓글 삭제 성공", response.body().toString())
                    response.body()
                } else {
                    Log.d("댓글 삭제 실패","댓글 삭제 실패")
                    null
                }

            } catch (e: IOException) {
                Log.e("연결 실패", "Network Error: ${e.message}", e)
                null
            } catch (e: Exception) {
                Log.e("오류 발생", "Unexpected Error: ${e.message}", e)
                null
            }
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    override fun getMyWhiskyList(
        name: String,
        category: String,
        date_order: String,
        name_order: String,
        score_order: String,
        callback: (ServerResponse<Any>?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun addWhiskyNameSearch(name: String, callback: (ServerResponse<List<WhiskyName>>?) -> Unit) {
        TODO("Not yet implemented")
    }
}
