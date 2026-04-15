package com.whiskeyReviewer.whiskeyreviewer.repository

import android.util.Log
import com.whiskeyReviewer.oneplusone.serverConnection.API
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.whiskeyReviewer.whiskeyreviewer.data.WhiskyReviewData
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiHandler
import com.whiskeyReviewer.whiskeyreviewer.utils.ApiResult
import com.whiskeyReviewer.whiskeyreviewer.utils.asThrowable

class PagingSource(
    private val apiService: API,
    private val searchWord: String?,
    private val detailSearchWord: String?,

    private val likeAsc: String?,
    private val scoreAsc: String?,
    private val createdAtAsc: String?
) : PagingSource<Int, WhiskyReviewData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WhiskyReviewData> {
        val page = params.key ?: 0
        Log.d("페이징1", page.toString())
        return try {

            val result = ApiHandler.makeApiCall(tag = "다른 유저 리뷰 목록 가져오기") {
                apiService.getSearchReviewList(
                    main_search_word = searchWord,
                    sub_search_word = detailSearchWord,
                    like_order = likeAsc,
                    score_order = scoreAsc,
                    create_order = createdAtAsc,
                    name_order = null,
                    page = page,
                    size = 10,
                )
            }

            return when (result) {
                is ApiResult.Success -> {
                    val data = result.data.data?.content ?: emptyList()
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 0) null else page,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                }
                else -> LoadResult.Error(result.asThrowable())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WhiskyReviewData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            Log.d("페이징1","페이징")
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 0
        }
    }
}