package com.example.ankiapp.dto;

import com.example.ankiapp.constant.UserEditMessage;
import com.example.ankiapp.entitiy.UserInfo;
import lombok.Data;

/**
 * ユーザー編集結果DTOクラス
 */
@Data
public class UserEditResult {
    
    /** ユーザー更新結果 */
    private UserInfo updateUserInfo;
    
    /** ユーザー更新結果メッセージEnum */
    private UserEditMessage updateMessage;

}
