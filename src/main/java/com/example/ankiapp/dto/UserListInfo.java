package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * ユーザー一覧画面DTOクラス
 */
@Data
public class UserListInfo {
    
    /**ログインID*/
    private String loginId;
    
    /**ログインが失敗した回数*/
    private int loginFailureCount;
    
    /**アカウントロック日時*/
    private LocalDateTime accountLockedTime;

    /**アカウント状態*/
    private String status;
    
    /**権限*/
    private String authority;
    
    /**登録日時*/
    private LocalDateTime createTime;

    /**更新日時*/
    private LocalDateTime updateTime;
    
    /**最終更新ユーザー*/
    private String updateUser;

}
