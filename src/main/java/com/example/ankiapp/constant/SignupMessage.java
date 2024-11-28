package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 */
@Getter
@AllArgsConstructor
public enum SignupMessage {
	SUCEED(MessageConst.SIGNUP_CONFIRM_COMPLETE, false),
	
	EXISTED_LOGIN_ID(MessageConst.SIGNUP_ALREADY_COMPLETED, true);
	
	private String messageId;
	
	private boolean isError;
}
