package com.onepage.coupong.presentation.user;

import com.onepage.coupong.business.user.dto.request.IdCheckReq;
import com.onepage.coupong.business.user.dto.request.SignInReq;
import com.onepage.coupong.business.user.dto.request.SignUpReq;
import com.onepage.coupong.business.user.dto.response.SignInResp;
import com.onepage.coupong.business.user.dto.response.TokenResp;
import org.springframework.http.ResponseEntity;

public interface UserUseCase {

    /* 아이디 중복 검사 */
    boolean isAvailableId(IdCheckReq idCheckReq);

    /* 회원가입 */
    boolean signUp(SignUpReq signUpReq);

    /* 로그인 */
    SignInResp signIn(SignInReq signInReq);

    /* 요청 헤더로부터 받은 Authorization 복호화 후 유저 정보 반환 */
    TokenResp tokenDecryption(String token);
}
