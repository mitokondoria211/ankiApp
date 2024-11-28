package com.example.ankiapp.dto;

import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import lombok.Data;


/**
 * ユーザー更新情報DTOクラス
 */
@Data
public class UserUpdateInfo {
    
    /**ログインID*/
    private String loginId;
    
    /**ログイン失敗状況をリセットするか(リセットするならtrue)*/
    private boolean resetLoginFailure;
    
    /**アカウント状態*/
    private UserStatusKind userStatusKind;
    
    /**権限*/
    private AuthorityKind authorityKind;
    
    /**更新ユーザーID*/
    private String updateUserId;
}
