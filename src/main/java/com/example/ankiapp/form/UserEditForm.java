package com.example.ankiapp.form;

import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import lombok.Data;


/**
 * ユーザー編集画面Formクラス
 */
@Data
public class UserEditForm {
    
    /**ログイン失敗状況をリセットするか？(リセットするならtrue)*/
    private boolean resetsLoginFailure;
    
    /**アカウント状態種別*/
    private  UserStatusKind userStatusKind;
    
    /**ユーザーの権限種別*/
    private AuthorityKind authorityKind;
}
