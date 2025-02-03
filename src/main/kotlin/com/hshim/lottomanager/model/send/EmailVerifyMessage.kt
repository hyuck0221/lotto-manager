package com.hshim.lottomanager.model.send

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.account.email.EmailVerify

class EmailVerifyMessage(
    title: String,
    html: String,
) : SendModel(title, html) {
    constructor(
        user: User,
        emailVerify: EmailVerify,
    ) : this(
        title = "${user.displayName}님의 계정에서 이메일 인증을 요청하였습니다.",
        html = """
            <h2>계정에 이메일 등록을 위한 인증번호 [${emailVerify.code}] 확인</h2>
            <p>3분 이내로 위 6자리 인증번호를 입력해주세요.</p>
            <br>
            <p>유저이름: ${user.displayName}</p>
            <p>요청 이메일: ${emailVerify.email}</p>
            <br>
            <p>본인 요청이 아닐 경우 아래 버튼을 클릭해주세요</p>
            <a href="https://lotto.hshim-universe.com/page/email/remove-code?id=${emailVerify.id}">인증코드 무효화</a>
        """.trimIndent()
    )
}