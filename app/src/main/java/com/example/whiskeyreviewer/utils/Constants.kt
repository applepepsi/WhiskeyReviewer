package com.example.nextclass.utils


//통신 결과
const val SUCCESS_CODE = 200
const val INVALID_JSON_FORMAT = "E00101" // 유효하지 않는 Json 형식입니다.
const val EMPTY_REQUEST_BODY = "E00102" // 요청 본문이 비어 있습니다
const val INVALID_PARAMETER_INCLUDED = "E00103" // 유효하지 않는 Parmeter가 포함되어 있습니다.
const val NO_PERMISSION_FOR_REQUEST = "E00104" // 해당 요청에 대한 권한이 없습니다.
const val INVALID_ACCESS_TOKEN = "E00105" // 유효하지 않은 Access 토큰입니다.
const val EXPIRED_ACCESS_TOKEN = "E00106" // 만료된 Access 토큰입니다.
const val INVALID_REFRESH_TOKEN = "E00107" // 유효하지 않은 Refresh 토큰입니다.
const val EXPIRED_REFRESH_TOKEN = "E00108" // 만료된 Refresh 토큰입니다.
const val INVALID_PARAMETER = "E00109" // {Parameter명}이(가) 유효하지 않습니다.
const val DUPLICATE_PARAMETER = "E00110" // {Parameter명}이(가) 중복되었습니다.
const val TOKEN_USER_NOT_EXIST = "E00111" // Token에 해당하는 회원이 존재하지 않습니다.
const val REQUEST_USER_NOT_EXIST = "E00201" // Request에 해당하는 회원이 존재하지 않습니다.
const val CURRENT_PASSWORD_NOT_MATCH = "E00202" // 현재 비밀번호가 일치하지 않습니다.
const val EMAIL_VERIFICATION_REQUIRED = "E00203" // 이메일 인증이 필요합니다.
const val CLASS_ALREADY_EXISTS_IN_TIMETABLE = "E00304" // 해당 수업이 시간표 상에 이미 존재합니다.
const val NO_PERMISSION_FOR_CLASS = "E00302" // 해당 수업에 대한 접근 권한이 없습니다.
const val CLASS_ALREADY_DELETED = "E00303" // 해당 수업은 이미 삭제되었습니다
const val TODO_LIST_ALREADY_EXISTS = "E00401" // 해당 To Do List가 이미 존재합니다
const val EMAIL_SEND_FAILED = "E00501" // 메일 발송에 실패하였습니다.
const val NO_EMAIL_FOR_VERIFICATION = "E00502" // 해당 인증건에 대한 발송 메일이 존재 하지 않습니다.
const val INVALID_VERIFICATION_CODE = "E00503" // 인증코드가 유효하지 않습니다.
const val USER_NOT_EXIST = "E00504" // 회원이 존재하지 않습니다.
const val VERIFICATION_CODE_ATTEMPTS_EXCEEDED = "E00505" // 인증코드 입력을 5회 실패하였습니다.


const val MaxTextCount = 64



