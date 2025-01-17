package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザー登録メッセージ種別
 */
@Getter
@AllArgsConstructor
public enum SignupMessage {
    
    /**登録成功*/
	SUCEED(MessageConst.SIGNUP_CONFIRM_COMPLETE, false),
	
	/**既に存在しているログインID*/
	EXISTED_LOGIN_ID(MessageConst.SIGNUP_ALREADY_COMPLETED, true);
	
    /**メッセージId*/
	private String messageId;
	
	/**エラーかどうか*/
	private boolean isError;
}
