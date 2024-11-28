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
    
    /**メールアドレス*/
    private String mailAddress;
}
