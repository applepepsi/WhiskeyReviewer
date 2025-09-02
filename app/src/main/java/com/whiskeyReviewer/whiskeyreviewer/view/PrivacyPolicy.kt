package com.whiskeyReviewer.whiskeyreviewer.view

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.whiskeyReviewer.whiskeyreviewer.component.customComponent.CustomAppBarComponent
import com.whiskeyReviewer.whiskeyreviewer.component.customIcon.CustomIconComponent
import com.whiskeyReviewer.whiskeyreviewer.component.home.NavigationDrawerLabel
import com.whiskeyReviewer.whiskeyreviewer.component.permission.PermissionItem
import com.whiskeyReviewer.whiskeyreviewer.component.permission.rememberPermissionList
import com.whiskeyReviewer.whiskeyreviewer.ui.theme.LightBlackColor
import com.whiskeyReviewer.whiskeyreviewer.viewModel.MainViewModel
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.whiskeyReviewer.whiskeyreviewer.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PrivacyPolicyView(
    mainViewModel:MainViewModel,
    navController: NavHostController,
){

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val permissionList = rememberPermissionList()
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissionList.map { it.permission }
    )
    Scaffold(
        modifier = Modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
                .verticalScroll(scrollState)

        ) {
            CustomAppBarComponent(
                titleTextValue = "설정",
                leftButton = {
                    CustomIconComponent(
                        icon = ImageVector.vectorResource(R.drawable.back_button_icon),
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                    )
                },
                rightButton = {
                    Spacer(modifier = Modifier.size(35.dp))
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "개인정보 처리방침",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "WhiskeyReviewer(이하 '회사')는 「개인정보 보호법」 등 관련 법령에 따라 이용자의 개인정보를 보호하고, 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 다음과 같이 개인정보 처리방침을 수립·공개합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제1조 (수집하는 개인정보의 항목 및 수집방법)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "① 회사는 회원가입, 원활한 고객 상담, 각종 서비스의 제공을 위해 최초 앱 실행시 아래와 같은 개인정보를 수집하고 있습니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "서비스 이용 과정에서 자동 생성 정보: 서비스 이용기록, 접속 로그, IP 주소, 기기 정보(OS, 기기 식별값), 광고 식별자(ADID/IDFA)"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "② 개인정보는 다음의 방법으로 수집합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "회원가입 및 서비스 이용 과정에서 이용자가 개인정보 수집에 대해 동의를 하고 직접 정보를 입력하는 경우"
                )
                Text(
                    text = "서비스 이용 과정에서 자동으로 수집되는 경우"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제2조 (개인정보의 수집 및 이용 목적)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "서비스 제공 및 회원 관리: 회원 식별, 서비스 이용 및 상담, 불량회원의 부정 이용 방지와 비인가 사용 방지"
                )
                Text(
                    text = "서비스 개선 및 신규 서비스 개발: 서비스 이용에 대한 통계 분석, 새로운 서비스 및 기능 개발"
                )
                Text(
                    text = "광고 제공: 이용자에게 맞춤형 광고를 포함한 광고 정보 제공 (광고 식별자 활용)"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제3조 (개인정보의 보유 및 이용기간)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자의 개인정보를 원칙적으로 회원 탈퇴 시 지체 없이 파기합니다. 단, 관계 법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계 법령에서 정한 일정한 기간 동안 회원정보를 보관합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "전자상거래 등에서의 소비자 보호에 관한 법률: 5년"
                )
                Text(
                    text = "통신비밀보호법: 3개월"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제4조 (개인정보의 제3자 제공)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자의 개인정보를 원칙적으로 외부에 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "이용자들이 사전에 동의한 경우"
                )
                Text(
                    text = "법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제5조 (이용자의 권리·의무 및 그 행사방법)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이용자는 언제든지 등록되어 있는 자신의 개인정보를 조회하거나 수정할 수 있으며 회원 탈퇴를 요청할 수도 있습니다. 정보의 수정 및 회원 탈퇴는 앱 내 '설정' 기능을 통해 가능합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제6조 (개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "① 회사는 이용자에게 개인화되고 맞춤화된 서비스를 제공하기 위해 광고 식별자(ADID/IDFA)를 수집 및 사용합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "② 광고 식별자는 사용자의 기기를 식별하는 값으로, 맞춤형 광고 제공을 위해 사용됩니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "③ 이용자는 언제든지 맞춤형 광고 수신을 거부할 수 있으며, 거부 시 일반 광고가 노출될 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Android: 설정 → 개인정보보호 → 광고 → 광고 ID 재설정 또는 삭제"
                )
                Text(
                    text = "iOS: 설정 → 개인정보보호 및 보안 → 추적 → 앱이 추적을 요청하도록 허용 끔"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제7조 (개인정보 보호책임자)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자의 개인정보를 보호하고 관련 불만을 처리하기 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "보호책임자: 임득환"
                )
                Text(
                    text = "문의: dlaejrtnz@naver.com"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "부칙",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "본 개인정보 처리방침은 2025년 9월 1일부터 시행됩니다."
                )
            }
        }
    }
}

