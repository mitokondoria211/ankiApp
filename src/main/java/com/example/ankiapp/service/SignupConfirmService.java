package com.example.ankiapp.service;

import com.example.ankiapp.constant.SignupConfirmStatus;


public interface SignupConfirmService {

    SignupConfirmStatus updateUserAsSignupCompletion(String loginId, String oneTimeCode);
    boolean resendOneTimeCode(String loginId);
    
    /**
     * ログインIDから仮登録ユーザーを確認します
     * 
     * @param loginId ログインID
     * @return 仮登録状態かどうか
     */
	boolean isTemporaryRegistrationUser(String loginId);
}
