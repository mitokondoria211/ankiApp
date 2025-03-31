package com.example.ankiapp.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ankiapp.constant.SignupConfirmStatus;
import com.example.ankiapp.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactional
public class SignupConfirmServiceImpl implements SignupConfirmService{
    
    /** ログイン情報テーブルDIO*/
    private final UserInfoRepository repository;
    
    /**パスワードエンコーダー*/
    private final PasswordEncoder passwordEncoder;

    /** ワンタイムコード有効時間 */
    @Value("${one-time-code.valid-time}")
    private Duration oneTimeCodeValidTime = Duration.ZERO;
    
    @Override
    public SignupConfirmStatus chkTentativeSignupUser(String loginId, String oneTimeCode) {
        var updateInfoOpt = repository.findById(loginId);
        if(updateInfoOpt.isEmpty()) {
            return SignupConfirmStatus.FAILURE_BY_NOT_EXISTS_TENTATIVE_USER;
        }
        var updateInfo = updateInfoOpt.get();
        
        if(!passwordEncoder.matches(oneTimeCode, updateInfo.getOneTimeCode())) {
            return SignupConfirmStatus.FAILURE_BY_WRONG_ONE_TIME_CODE;
        }
        var oneTimeCodeTimeLimit = updateInfo.getOneTimeCodeSendTime().plus(oneTimeCodeValidTime);
        boolean isOneTimeCodeAvailable = oneTimeCodeTimeLimit.isAfter(LocalDateTime.now());
        if(!isOneTimeCodeAvailable) {
            return SignupConfirmStatus.FAILURE_BY_EXPIRED_ONE_TIME_CODE;
        }
        
        updateInfo.setSignupCompleted(true);
        updateInfo.setOneTimeCode(null);
        updateInfo.setOneTimeCodeSendTime(null);
        updateInfo.setUpdateTime(LocalDateTime.now());
        updateInfo.setUpdateUser(loginId);
        
        try {
          repository.save(updateInfo);  
        } catch(Exception e) {
            return SignupConfirmStatus.FAILURE_BY_DB_ERROR;
        }
        
        return SignupConfirmStatus.SUCCEED;
    }

}
