package com.example.whiskeyreviewer.data


//자원 절약, 의미없게 리사이즈 하는것 방지, 재활용 불가 이슈로 예전이미지인지 판단하는 데이터타입 정의
data class ImageData(
    val image:ByteArray,
    val isOldImage:Boolean,
)
