package com.example.whiskeyreviewer.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whiskeyreviewer.R

sealed class NavigationDrawerItems(
    val title: String,
    val icon: Int,
    val screenRoute: String
) {

    data object Setting : NavigationDrawerItems("설정", R.drawable.setting_icon, SETTING)
    data object Backup : NavigationDrawerItems("데이터 백업", R.drawable.backup_icon, BACKUP)

    data object InsertBackupCode : NavigationDrawerItems("백업 코드 입력", R.drawable.insert_backup_code_icon, INSERT_BACKUP_CODE)
    companion object {
        const val SETTING = "SETTING"
        const val BACKUP = "BACKUP"
        const val INSERT_BACKUP_CODE = "INSERT_BACKUP_CODE"
    }
}



