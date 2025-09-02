package com.whiskeyReviewer.whiskeyreviewer.data

import com.whiskeyReviewer.whiskeyreviewer.R


sealed class NavigationDrawerItems(
    val title: String,
    val icon: Int,
    val screenRoute: String
) {

    data object Setting : NavigationDrawerItems("설정", R.drawable.setting_icon, SETTING)
    data object Backup : NavigationDrawerItems("데이터 백업", R.drawable.backup_icon, BACKUP)
    data object InsertBackupCode : NavigationDrawerItems("백업 코드 입력", R.drawable.insert_backup_code_icon, INSERT_BACKUP_CODE)

    data object PrivacyPolicy : NavigationDrawerItems("개인정보 처리방침", R.drawable.policy_icon, PRIVACY_POLICY)

    data object TermsOfService : NavigationDrawerItems("이용 약관", R.drawable.policy_icon, TERMS_OF_SERVICE)

    companion object {
        const val SETTING = "SETTING"
        const val BACKUP = "BACKUP"
        const val INSERT_BACKUP_CODE = "INSERT_BACKUP_CODE"
        const val PRIVACY_POLICY = "PRIVACY_POLICY"
        const val TERMS_OF_SERVICE = "TERMS_OF_SERVICE"
    }
}



