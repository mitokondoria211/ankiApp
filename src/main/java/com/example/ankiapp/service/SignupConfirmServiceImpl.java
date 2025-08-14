package com.example.ankiapp.service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.SignupConfirmStatus;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactional
public class SignupConfirmServiceImpl implements SignupConfirmService{
    
    /** ログイン情報テーブルDIO*/
    private final UserInfoRepository repository;
    
    /**パスワードエンコーダー*/
    private final PasswordEncoder passwordEncoder;
    
    /** メール送信サービス */
    private final MailSendService mailSendService;
    
    /** メッセージソース */
    private final MessageSource messageSource;

    /** ワンタイムコード有効時間 */
    @Value("${one-time-code.valid-time}")
    private Duration oneTimeCodeValidTime = Duration.ZERO;
    
    /** ワンタイムコードの長さ */
    @Value("${one-time-code.length}")
    private int oneTimeCodeLength;
    
    @Override
    public SignupConfirmStatus updateUserAsSignupCompletion(String loginId, String oneTimeCode) {
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

    @Override
    public boolean resendOneTimeCode(String loginId) {
        // ユーザーの存在確認
        var userInfoOpt = repository.findById(loginId);
        if (userInfoOpt.isEmpty() || userInfoOpt.get().isSignupCompleted()) {
            return false;
        }
        
        var userInfo = userInfoOpt.get();
        
        // 新しいワンタイムコードを生成
        String newOneTimeCode = AppUtility.generatedRandomString(oneTimeCodeLength);
        
        // ユーザー情報を更新
        userInfo.setOneTimeCode(passwordEncoder.encode(newOneTimeCode));
        userInfo.setOneTimeCodeSendTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setUpdateUser(loginId);
        
        try {
            repository.save(userInfo);
        } catch (Exception e) {
            return false;
        }
        
        // メール送信
        var mailTextBase = AppUtility.getMessage(messageSource, MessageConst.SIGNUP_MAIL_TEXT);
        var mailText = MessageFormat.format(mailTextBase, newOneTimeCode, oneTimeCodeValidTime.toMinutes());
        var mailSubject = AppUtility.getMessage(messageSource, MessageConst.SIGNUP_MAIL_SUBJECT);
        
        return mailSendService.sendMail(userInfo.getMailAddress(), mailSubject, mailText);
    }
    
    @Override
    public boolean isTemporaryRegistrationUser(String loginId) {
        var userInfoOpt = repository.findById(loginId);
        
        // ユーザーが存在し、仮登録状態（signup_completed = false）の場合はtrue
        return userInfoOpt.isPresent() && !userInfoOpt.get().isSignupCompleted();
    }

	@Override
	public boolean verifyTemporaryUserPassword(String loginId, String password) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
    
    

}
