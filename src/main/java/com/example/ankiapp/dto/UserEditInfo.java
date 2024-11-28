package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserEditInfo {
    /**ログインID*/
    private String loginId;
    
    /**ログイン失敗回数*/
    private int loginFailureCount;
    
    /**アカウントロック日時*/
    private LocalDateTime accountLockedTime;

}
