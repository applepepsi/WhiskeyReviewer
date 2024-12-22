package com.example.whiskeyreviewer.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whiskeyreviewer.R

sealed class TapLayoutItems(
    val title: String,

) {
    data object AllWhiskey : TapLayoutItems("전체")
    data object ScotchWhiskey : TapLayoutItems("스카치")
    data object IrishWhiskey : TapLayoutItems("아이리시")
    data object AmericanWhiskey : TapLayoutItems("아메리칸")
    data object CanadianWhiskey : TapLayoutItems("커네이디언")

    data object MaltWhiskey : TapLayoutItems("몰트")
    data object GrainWhiskey : TapLayoutItems("그레인")
    data object BlendedWhiskey : TapLayoutItems("블렌디드")

    data object PortWhiskey : TapLayoutItems("포트")
    data object PatentWhiskey : TapLayoutItems("페이턴트")

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



