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
fun TermsOfServiceView(
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
                    text = "이용 약관",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "제1조 (목적)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이 약관은 WhiskeyReviewer(이하 '회사' 라고 합니다)가 제공하는 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제2조 (정의)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "'서비스'라 함은 구현되는 단말기(PC, TV, 휴대형단말기 등의 각종 유무선 장치를 포함)와 상관없이 '이용자'가 이용할 수 있는 회사가 제공하는 제반 서비스를 의미합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "'이용자'란 약관에 따라 회사가 제공하는 서비스를 받는 '회원'을 말합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "'회원'은 회사가 제공하는 서비스를 이용하는 자를 말합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "'회원 아이디(ID)'라 함은 회원의 식별과 서비스 이용을 위하여 회사가 정하고 승인하는 문자 또는 숫자의 조합을 의미합니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "'콘텐츠'란 정보통신망법의 규정에 따라 정보통신망에서 사용되는 부호·문자·음성·음향·이미지 또는 영상 등으로 정보 형태의 글, 사진, 동영상 및 각종 파일과 링크 등을 말합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제3조 (약관 외 준칙)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이 약관에서 정하지 아니한 사항에 대해서는 법령 또는 회사가 정한 서비스의 개별약관, 운영정책 및 규칙 등(이하 '세부지침')의 규정에 따릅니다. 또한 본 약관과 세부지침이 충돌할 경우에는 세부지침에 따릅니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제4조 (약관의 효력 및 변경)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이 약관은 WhiskeyReviewer(이)가 제공하는 모든 인터넷서비스에 게시하여 공시합니다. '회사'는 '전자상거래 등에서의 소비자보호에 관한 법률(이하 '전자상거래법'이라 함)', '약관의 규제에 관한 법률(이하 '약관규제법'이라 함)', '전자문서 및 전자거래 기본법(이하 '전자문서법'이라 함)', '전기통신기본법', '정보통신망 이용촉진 및 정보보호 등에 관한 법률(이하 '정보통신망법'이라 함)' 등 관련 법령 등 관계 법령에 위배되지 않는 범위 내에서 이 약관을 변경할 수 있으며, 회사가 약관을 변경하는 경우 변경된 약관의 내용과 시행일을 정하여, 그 시행일로부터 최소 7일 (이용자에게 불리하거나 중대한 사항의 변경은 30일) 이전부터 시행일 후 상당한 기간 동안 공지하고, 기존 이용자에게는 변경된 약관, 적용일자 및 변경사유(변경될 내용 중 중요사항에 대한 설명을 포함)를 별도의 전자적 수단(전자우편, 문자메시지, 서비스 내 고지화면, 알림 메시지를 보내는 등의 방법)으로 개별 통지합니다. 변경된 약관은 공지하거나 통지한 시행일로부터 효력이 발생합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 제1항에 따라 개정약관을 공지 또는 통지하는 경우 '변경에 동의하지 아니할 경우 공지일 또는 통지를 받은 날로부터 7일 (이용자에게 불리하거나 중대한 사항의 변경인 경우에는 30일) 내에 계약을 해지할 수 있으며, 계약해지의 의사표시를 하지 아니한 경우에는 변경에 동의한 것으로 본다.' 라는 취지의 내용을 함께 통지합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이용자가 제2항의 공지일 또는 통지를 받은 날로부터 7일(또는 이용자에게 불리하거나 중대한 사항의 변경인 경우에는 30일)내에 변경된 약관에 대해 거절의 의사를 표시하지 않았을 때에는 본 약관의 변경에 동의한 것으로 간주합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제5조 (개인정보보호)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자들의 개인정보를 중요시하며, 정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법 등 관련 법규를 준수하기 위해 노력합니다. 회사는 개인정보보호정책을 통하여 이용자가 제공하는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 이용자의 개인정보의 보호 및 사용에 대해서는 관련 법규 및 회사의 개인정보처리방침을 적용합니다. 다만, 회사에서 운영하는 웹 사이트 등에서 링크된 외부 웹페이지에서는 회사의 개인정보처리방침이 적용되지 않습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제6조 (서비스의 제공)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사의 서비스는 연중무휴, 1일 24시간 제공을 원칙으로 합니다. 다만 회사 시스템의 유지 보수를 위한 점검, 통신장비의 교체 등 특별한 사유가 있는 경우 서비스의 전부 또는 일부에 대하여 일시적인 제공 중단이 발생할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 제공하는 개별 서비스에 대한 구체적인 안내사항은 개별 서비스 화면에서 확인할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 제공하는 서비스의 내용은 다음과 같습니다. 가. 앱 사용자가 위스키를 검색하여 후기를 작성하는 서비스 나. 앱 사용자가 작성한 후기에 대해 다른 사용자들이 공유할 수 있는 서비스"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제7조 (서비스의 제한 등)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 전시, 사변, 천재지변 또는 이에 준하는 국가비상사태가 발생하거나 발생할 우려가 있는 경우와 전기통신사업법에 의한 기간통신사업자가 전기통신서비스를 중지하는 등 부득이한 사유가 있는 경우에는 서비스의 전부 또는 일부를 제한하거나 중지할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "무료서비스는 전항의 규정에도 불구하고, 회사의 운영정책 등의 사유로 서비스의 전부 또는 일부가 제한되거나 중지될 수 있으며, 유료로 전환될 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 서비스의 이용을 제한하거나 정지하는 때에는 그 사유 및 제한기간, 예정 일시 등을 지체없이 이용자에게 알립니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 사전에 결제정보를 입력 받고, 무료로 제공중인 서비스를 유료로 전환할 경우, 그 사유와 유료 전환 예정 일시를 통지하고 유료 전환에 대한 이용자의 동의를 받습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제8조 (손해배상)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자 또는 상대방의 귀책에 따라 손해가 발생하는 경우 손해배상을 청구할 수 있습니다. 다만, 회사는 무료서비스의 장애, 제공 중단, 보관된 자료 멸실 또는 삭제, 변조 등으로 인한 손해에 대하여는 배상 책임을 부담하지 않습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 제공하는 서비스의 이용과 관련하여 회사의 운영정책 및 개인 정보 보호정책, 기타 서비스별 이용약관에서 정하는 내용에 위반하지 않는 한 회사는 어떠한 손해에 대하여도 책임을 부담하지 않습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제9조 (면책사항)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임을 지지 않습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자의 귀책사유로 인한 서비스 이용장애에 대하여 책임을 지지 않습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자가 서비스를 이용하여 기대하는 수익을 얻지 못한 것에 대하여 책임 지지 않으며 서비스를 통하여 얻은 자료로 인한 손해 등에 대하여도 책임을 지지 않습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자가 웹페이지에 게재한 내용의 신뢰도, 정확성 등 내용에 대해서는 책임지지 않으며, 이용자 상호간 또는 이용자와 제3자 상호간 서비스를 매개로 발생한 분쟁에 개입하지 않습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제10조 (정보의 제공 및 광고의 게재)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 이용자가 서비스 이용 중 필요하다고 인정되는 각종 정보 및 광고를 배너 게재, 전자우편(E-Mail), 휴대폰 메시지, 전화, 우편 등의 방법으로 이용자에게 제공(또는 전송)할 수 있습니다. 다만, 이용자는 이를 원하지 않을 경우 회사가 제공하는 방법에 따라 수신을 거부할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이용자가 수신 거부를 한 경우에도 이용약관, 개인정보보호정책, 기타 이용자의 이익에 영향을 미칠 수 있는 중요한 사항의 변경 등 '정보통신망 이용촉진 및 정보보호 등에 관한 법률'에서 정하는 사유 등 이용자가 반드시 알고 있어야 하는 사항에 대하여는 전자우편 등의 방법으로 정보를 제공할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "제1항 단서에 따라 이용자가 수신 거부 조치를 취한 경우 이로 인하여 회사가 거래 관련 정보, 이용 문의에 대한 답변 등의 정보를 전달하지 못한 경우 회사는 이로 인한 책임이 없습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 ‘정보통신망법’ 시행령에 따라 2년마다 광고성 정보의 수신동의 여부를 확인합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 광고주의 판촉 활동에 이용자가 참여하거나, 거래의 결과로서 발생하는 손실 또는 손해에 대하여는 책임을 지지 않습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제11조 (권리의 귀속)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사가 제공하는 서비스에 대한 저작권 등 지식재산권은 회사에 귀속 됩니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 서비스와 관련하여 이용자에게 회사가 정한 조건 따라 회사가 제공하는 서비스를 이용할 수 있는 권한만을 부여하며, 이용자는 이를 양도, 판매, 담보제공 하는 등 처분행위를 할 수 없습니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "제1항의 규정에도 불구하고 이용자가 직접 작성한 콘텐츠 및 회사의 제휴계약에 따라 제공된 저작물에 대한 지식재산권은 회사에 귀속되지 않습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제12조 (콘텐츠의 관리)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회원이 작성 또는 창작한 콘텐츠가 '개인정보보호법' 및 '저작권법' 등 관련 법에 위반되는 내용을 포함하는 경우, 관리자는 관련 법이 정한 절차에 따라 해당 콘텐츠의 게시중단 및 삭제 등을 요청할 수 있으며, 회사는 관련 법에 따라 조치를 취하여야 합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "회사는 전항에 따른 권리자의 요청이 없는 경우라도 권리침해가 인정될 만한 사유가 있거나 기타 회사 정책 및 관련 법에 위반되는 경우에는 관련 법에 따라 해당 콘텐츠에 대해 임시조치 등을 취할 수 있습니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제13조 (콘텐츠의 저작권)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이용자가 서비스 내에 게시한 콘텐츠의 저작권은 해당 콘텐츠의 저작자에게 귀속됩니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "제1항에 불구하고 회사는 서비스의 운영, 전시, 전송, 배포, 홍보 등의 목적으로 별도의 허락 없이 무상으로 저작권법 및 공정한 거래관행에 합치되는 범위 내에서 다음 각 호와 같이 회원이 등록한 콘텐츠를 사용할 수 있습니다. 가. 서비스 내에서 이용자가 작성한 콘텐츠의 복제, 수정, 전시, 전송, 배포 등 저작권을 침해하지 않는 범위 내의 2차적 저작물 또는 편집 저작물 작성을 위한 사용. 다만, 해당 콘텐츠를 등록한 이용자가 해당 콘텐츠의 삭제 또는 사용 중지를 요청하는 경우 회사는 관련 법에 따라 보존해야하는 사항을 제외하고 관련 콘텐츠를 모두 삭제 또는 사용중지합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제14조 (회사의 의무)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회사”는 본 약관 및 관련 법령에서 금지하는 행위 및 미풍양속에 반하는 행위를 하지 않으며, 계속적이고 안정적인 “서비스”의 제공을 위하여 최선을 다하여 노력합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회사”는 “회원”이 안전하게 “서비스”를 이용할 수 있도록 일체의 개인정보 보호를 위한 보안시스템을 갖추어야 하며 개인정보처리방침을 공시하고 준수합니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회사”는 “회원”으로부터 제기되는 의견이나 불만이 정당하다고 객관적으로 인정될 경우에는 합리적인 기간 내에 신속하게 처리하여야 합니다. 다만, 처리에 장기간이 소요되는 경우 “회원”에게 게시판 또는 전자우편 등을 통하여 지체 사유를 안내하고 처리과정 및 처리결과를 전달합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제15조 (회원의 의무)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회원”은 “회사”에서 제공하는 “서비스”를 본래의 이용 목적 이외의 용도로 사용하거나 다음 각 호에 해당하는 행위를 해서는 안됩니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "1. “회사”의 “서비스” 및 제공된 애플리케이션을 이용하여 얻은 정보를 “회사”의 사전 승낙없이 복제, 유통, 조장하거나 상업적으로 이용하는 행위 또는 알려지거나 알려지지 않은 버그를 악용하여 “서비스”를 이용하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "2. “회사” 및 제3자의 명예를 훼손하거나 업무를 방해하는 행위 또는 회사 및 제3자에게 손해를 가하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "3. “회사” 또는 제3자의 지적재산권, 초상권 등 기타 권리를 침해하거나 “회사”의 “서비스”를 통하지 않고 다른 “회원”의 개인정보를 수집, 저장, 유포, 게시하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "4. 제3자를 기망하여 이득을 취하거나 “회사”가 제공하는 “서비스”를 불건전하게 이용하여 제3자에게 피해를 주는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "5. 음란, 저속한 정보를 교류, 게재하거나 그에 관해 연결(링크)하거나 사회통념상 타인에게 불쾌감을 줄 수 있는 내용을 담은 광고 및 홍보물을 게재하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "6. 재물을 걸고 도박하는 등 사해행위를 유도하거나 참여하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "7. 수치심이나 혐오감 또는 공포심을 일으키는 말이나 음향, 글, 화상 또는 영상을 게재하거나 상대방에게 전송, 도달, 유포하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "8. 관련 법령에 의하여 전송 또는 게시가 금지되는 정보 또는 컴퓨터 소프트웨어, 하드웨어, 전기통신장비의 정상적인 가동을 방해, 파괴할 목적으로 고안된 소프트웨어 바이러스 기타 다른 컴퓨터 코드, 파일, 프로그램을 포함하고 있는 자료를 전송, 게시, 유포, 사용하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "9. “회사”로부터 특별한 권리를 부여받지 않고 애플리케이션을 변경하거나 애플리케이션에 다른 프로그램을 추가 또는 삽입하거나 서버를 해킹, 역설계, 소스코드나 애플리케이션 데이터의 유출 및 변경, 별도의 서버를 구축하거나 웹사이트의 일부분을 임의로 변경 또는 도용하여 “회사”를 사칭하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "10. “회사”의 동의 없이 영리, 영업, 광고, 정치활동, 불법선거운동 등을 목적으로 “서비스”를 이용하는 행위"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "11. 기타 공공질서 및 미풍양속을 위반하거나 불법적, 부당한 행위 및 법령에 위배되는 행위"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회원”은 “회사” 홈페이지 상의 공지사항 및 이용약관의 수정사항 등을 확인하고 이를 준수할 의무가 있으며 기타 “회사”의 업무에 방해되는 행위를 하여서는 안 됩니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회원”의 계정에 관한 관리 책임은 “회원”에게 있으며, 이를 제3자가 이용하도록 하여서는 안 됩니다."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“회사”는 제1항, 제2항 및 다음 각 호의 어느 하나에 해당하는 행위의 구체적인 유형을 운영 정책에서 정할 수 있으며, 회원은 이를 준수할 의무가 있습니다."
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1. 게시판 이용 등에 대한 제한"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "2. 기타 “회원”의 “서비스” 이용에 대한 본질적인 권리를 침해하지 않는 범위 내에서 “회사”가 운영상 필요하다고 인정되는 사항"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "제16조 (관할법원 및 준거법)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "서비스와 관련하여 분쟁이 발생한 경우 관할법원은 민사소송법에 따른 관할법원으로 정하며, 준거법은 대한민국의 법령을 적용합니다."
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "부칙",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "제1조(시행일) 본 약관은 2025.09.01.부터 시행됩니다."
                )
            }
        }
    }
}

