package com.example.ankiapp.dto;

import lombok.Data;

/**
 * ユーザー登録用DTOクラス
 */

@Data
public class SignupInfo {
    
    /**ログインID*/
    private String loginId;
    
    /**パスワード*/
    private String password;
    
    /**確認パスワード*/
	private String confirmPassword;
    
    /**メールアドレス*/
    private String mailAddress;
}
