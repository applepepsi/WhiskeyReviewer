package com.example.whiskeyreviewer.data.pagingResponse

import com.example.whiskeyreviewer.data.WhiskyReviewData

data class PagingResponse(
    val content: List<WhiskyReviewData>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val first: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val empty: Boolean
)
