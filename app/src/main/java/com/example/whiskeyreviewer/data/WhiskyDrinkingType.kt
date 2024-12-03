package com.example.whiskeyreviewer.data

sealed class WhiskyDrinkingType(val type: String) {
    data object OnTheRocks : WhiskyDrinkingType("온더락")
    data object Straight : WhiskyDrinkingType("스트레이트")
    data object Highball : WhiskyDrinkingType("하이볼")
    data object OldFashioned : WhiskyDrinkingType("올드 패션드")
    data object Manhattan : WhiskyDrinkingType("맨하탄")
    data object WhiskeySour : WhiskyDrinkingType("위스키 사워")
    data object WithMixer : WhiskyDrinkingType("믹서와 함께")
}
