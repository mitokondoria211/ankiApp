package com.example.ankiapp.dto;

import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import lombok.Data;

/**
 * ユーザー一覧検索用DTOクラス
 */
@Data
public class UserSearchInfo {
    /**ログインID*/
    private String loginId;
    
    /**アカウント状態*/
    private UserStatusKind userStatusKind;
    
    /**権限*/
    private AuthorityKind authorityKind;
}
