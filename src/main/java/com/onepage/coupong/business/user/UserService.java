package com.onepage.coupong.business.user;

import com.onepage.coupong.business.user.dto.request.IdCheckReq;
import com.onepage.coupong.business.user.dto.request.SignInReq;
import com.onepage.coupong.business.user.dto.request.SignUpReq;
import com.onepage.coupong.business.user.dto.response.SignInResp;
import com.onepage.coupong.business.user.dto.response.TokenResp;
import com.onepage.coupong.implementation.user.manager.MailManager;
import com.onepage.coupong.implementation.user.manager.SignInManager;
import com.onepage.coupong.implementation.user.manager.SignUpManager;
import com.onepage.coupong.infrastructure.auth.manager.TokenManager;
import com.onepage.coupong.jpa.user.User;
import com.onepage.coupong.presentation.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final SignUpManager signUpManager;
    private final SignInManager signInManager;
    private final MailManager mailManager;
    private final TokenManager tokenManager;

    /* 아이디 중복 검사 확인 */
    @Override
    public boolean isAvailableId(IdCheckReq idCheckReq) {
        signUpManager.isAvailableId(idCheckReq.getUsername());
        return true;
    }

    /* 회원가입 */
    @Override
    public boolean signUp(SignUpReq signUpReq) {
        signUpManager.isAvailableId(signUpReq.getUsername());
        signUpManager.isAvailablePassword(signUpReq.getPassword(), signUpReq.getPasswordCheck());
        mailManager.isAvailableCertification(signUpReq.getUsername(), signUpReq.getCertification());
        signUpManager.registerUser(signUpReq);
        return true;
    }

    /* 로그인 */
    @Override
    public SignInResp signIn(SignInReq signInReq) {
        return signInManager.signIn(signInReq);
    }

    @Override
    public TokenResp tokenDecryption(String token) {
        User user = tokenManager.tokenToUser(token);

        return TokenResp.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .logintype(user.getType())
                .build();
    }
}
