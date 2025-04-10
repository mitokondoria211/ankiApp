package com.example.ankiapp.service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.SignupResult;
import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import com.example.ankiapp.dto.SignupInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;


/**
 * ユーザー登録画面 Service実装クラス
 */

@Service
@RequiredArgsConstructor
//@Transactional
public class SignupServiceImpl implements SignupService {
    
    /** メール送信Serviceクラス */
    private final MailSendService mailSendService;
    
	/** ログイン情報テーブルDIO*/
	private final UserInfoRepository repository;
	
	/**Dozer Mapper*/
	private final Mapper mapper;
	
	/**パスワードエンコーダー*/
	private final PasswordEncoder passwordEncoder;
	
	   /**メッセージソース*/
    private final MessageSource messageSource;
    
    /** ワンタイムコード有効時間 */
    @Value("${one-time-code.valid-time}")
    private Duration oneTimeCodeValidTime = Duration.ZERO;

    /** ワンタイムコードの長さ */
    @Value("${one-time-code.length}")
    private int oneTimeCodeLength;

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public Optional <UserInfo> resistUserInfo(SignupForm form) {
//		var 	userInfoExistedOpt =repository.findById(form.getLoginId());
//		if(userInfoExistedOpt.isPresent()) {
//			return Optional.empty();
//		}
//		var userInfo = mapper.map(form, UserInfo.class);
//		var encodedPassword = passwordEncoder.encode(form.getPassword());
//		userInfo.setPassword(encodedPassword);
//		userInfo.setUserStatusKind(UserStatusKind.ENABLED);
//		userInfo.setAuthorityKind(AuthorityKind.ANKI_MANEGER);
//		userInfo.setCreateTime(LocalDateTime.now());
//		userInfo.setUpdateTime(LocalDateTime.now());
//		userInfo.setUpdateUser(form.getLoginId());
//
//		return Optional.of(repository.save(userInfo));
//	}

    @Override
    public SignupResult signup(SignupInfo dto) {
        var userInfoOpt = repository.findById(dto.getLoginId());
        if(userInfoOpt.isPresent()) {
            var userInfo = userInfoOpt.get();
            if(userInfo.isSignupCompleted()) {
                return SignupResult.FAILURE_BY_ALREADY_COMPLETED;
            }
            return SignupResult.FAILURE_BY_SIGNUP_PROCEEDING;
        }
        
        var oneTimeCode = generatedRandomString();
        var signupInfo = editSignupInfo(dto, oneTimeCode);
        try {
            repository.save(signupInfo);
        }catch (Exception e){
            return SignupResult.FAILURE_BY_DB_ERROR;
        }
        
        //ユーザーがメール本文からアクセスする本登録用のリンクを作成
        var mailTextBase = AppUtility.getMessage(messageSource, MessageConst.SIGNUP_MAIL_TEXT);
        var mailText = MessageFormat.format(mailTextBase, oneTimeCode, oneTimeCodeValidTime.toMinutes());     
        var mailSubject = AppUtility.getMessage(messageSource, MessageConst.SIGNUP_MAIL_SUBJECT);
        var canSendMail = mailSendService.sendMail(dto.getMailAddress(), mailSubject, mailText);
        
        if(!canSendMail) {
            var isDeleteFailure = deleteSignupInfo(dto.getLoginId());
            if(isDeleteFailure) {
                return SignupResult.FAILURE_BY_DB_ERROR;
            }
            return SignupResult.FAILURE_BY_MAIL_SEND_ERROR;
        }
        return SignupResult.SUCCEED;
    }
    
    private boolean deleteSignupInfo(String loginId) {
        try {
            repository.deleteById(loginId);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }

    /**
     * ユーザー登録情報を作成する
     * @param dto ユーザー登録画面Serivice入力情報
     * @param oneTimeCode ワンタイムコード
     * @return ユーザー登録情報
     */
    private UserInfo editSignupInfo(SignupInfo dto, String oneTimeCode) {
      var userInfo = new UserInfo();
      userInfo.setLoginId(dto.getLoginId());
      userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
      userInfo.setMailAddress(dto.getMailAddress());
      userInfo.setOneTimeCode(passwordEncoder.encode(oneTimeCode));
      userInfo.setOneTimeCodeSendTime(LocalDateTime.now());
      userInfo.setUserStatusKind(UserStatusKind.ENABLED);
      userInfo.setAuthorityKind(AuthorityKind.ANKI_MANEGER);
      userInfo.setSignupCompleted(false);
      userInfo.setCreateTime(LocalDateTime.now());
      userInfo.setUpdateTime(LocalDateTime.now());
      userInfo.setUpdateUser(dto.getLoginId());
      
        return userInfo;
    }

    private String generatedRandomString() {
        var sb = new StringBuilder();
        for (int i = 0; i < oneTimeCodeLength; i++) {
            var randomNum = ThreadLocalRandom.current().nextInt(10);
            sb.append(randomNum);
        }
        return sb.toString();
    }
    
    
	

}
