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
    data object AllWhiskey : TapLayoutItems("전체",null)

    data object SingleMalt : TapLayoutItems("싱글몰트", "Single Malt")

    data object BlendedMalt : TapLayoutItems("블렌디드 몰트", "Blended Malt")

    data object Blended : TapLayoutItems("블렌디드", "Blended")

    data object BlendedGrain : TapLayoutItems("블렌디드 그레인", "Blended Grain")

    data object Grain : TapLayoutItems("그레인", "Grain")

    data object Bourbon : TapLayoutItems("버번", "Bourbon")

    data object Rye : TapLayoutItems("라이", "Rye")

    data object Corn : TapLayoutItems("콘", "Corn")
    data object American : TapLayoutItems("아메리칸", "American")

    data object Wheat : TapLayoutItems("위트", "Wheat")

    data object CanadianWhiskey : TapLayoutItems("캐나디안", "Canadian")

    data object SinglePotStill : TapLayoutItems("싱글 팟 스틸", "Single Pot Still")

    data object Tennessee : TapLayoutItems("테네시", "Tennessee")

    data object Spirit : TapLayoutItems("스피릿", "Spirit")


    data object Other : TapLayoutItems("기타", "Other")
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



