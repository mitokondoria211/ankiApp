package com.example.ankiapp.service;

import com.example.ankiapp.constant.SignupConfirmStatus;


public interface SignupConfirmService {

    SignupConfirmStatus chkTentativeSignupUser(String loginId, String oneTimeCode);

}
