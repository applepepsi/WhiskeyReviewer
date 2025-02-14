package com.example.whiskeyreviewer.data

import com.example.whiskeyreviewer.R

sealed class CountryItems(
    val country: String,
    val icon: Int,
){
    data object Scotland : CountryItems("스코틀랜드", R.drawable.sct,)
    data object Ireland : CountryItems("아일랜드", R.drawable.ie,)
    data object America : CountryItems("미국", R.drawable.us,)
    data object Canada : CountryItems("캐나다", R.drawable.ca, )
    data object Japan : CountryItems("일본", R.drawable.jp,)
    data object Taiwan : CountryItems("대만", R.drawable.tw, )
    data object India : CountryItems("인도", R.drawable.india, )
    data object Germany : CountryItems("독일", R.drawable.germany, )
    data object Australia : CountryItems("호주", R.drawable.au, )

}

