package com.example.ankiapp.service;

import com.example.ankiapp.constant.SignupConfirmStatus;


public interface SignupConfirmService {

    SignupConfirmStatus updateUserAsSignupCompletion(String loginId, String oneTimeCode);
    boolean resendOneTimeCode(String loginId);
}
