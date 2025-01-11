package com.example.whiskeyreviewer.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whiskeyreviewer.R

sealed class TapLayoutItems(
    val title: String,
    val name: String?
) {
//
//    data object ScotchWhiskey : TapLayoutItems("스카치")
//    data object IrishWhiskey : TapLayoutItems("아이리시")
//    data object AmericanWhiskey : TapLayoutItems("아메리칸")
//    data object CanadianWhiskey : TapLayoutItems("커네이디언")
//
//    data object MaltWhiskey : TapLayoutItems("몰트")
//    data object GrainWhiskey : TapLayoutItems("그레인")
//    data object BlendedWhiskey : TapLayoutItems("블렌디드")
//
//    data object PortWhiskey : TapLayoutItems("포트")
//    data object PatentWhiskey : TapLayoutItems("페이턴트")

    data object AllWhiskey : TapLayoutItems("전체",null)
    data object AmericanWhiskey : TapLayoutItems("아메리칸", "American Whiskey")
    data object Blend : TapLayoutItems("블렌드", "Blend")
    data object BlendedGrain : TapLayoutItems("블렌디드 그레인", "Blended Grain")
    data object BlendedMalt : TapLayoutItems("블렌디드 몰트", "Blended Malt")
    data object Bourbon : TapLayoutItems("버번", "Bourbon")
    data object CanadianWhiskey : TapLayoutItems("커네이디언", "Canadian Whisky")
    data object Corn : TapLayoutItems("옥수수", "Corn")
    data object Rice : TapLayoutItems("쌀", "Rice")
    data object Rye : TapLayoutItems("호밀", "Rye")
    data object SingleGrain : TapLayoutItems("싱글 그레인", "Single Grain")
    data object SingleMalt : TapLayoutItems("싱글 몰트", "Single Malt")
    data object SinglePotStill : TapLayoutItems("싱글 포트 스틸", "Single Pot Still")
    data object Spirit : TapLayoutItems("스피릿", "Spirit")
    data object Tennessee : TapLayoutItems("테네시", "Tennessee")
    data object Wheat : TapLayoutItems("밀", "Wheat")
    companion object {
//        val allWhiskyType = listOf(
//            AllWhiskey,
//            ScotchWhiskey,
//            IrishWhiskey,
//            AmericanWhiskey,
//            CanadianWhiskey,
//            MaltWhiskey,
//            GrainWhiskey,
//            BlendedWhiskey,
//            PortWhiskey,
//            PatentWhiskey
//        )
    }

}



