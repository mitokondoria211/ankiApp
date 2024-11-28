package com.example.ankiapp.form;


import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import lombok.Data;

/**
 * ユーザー一覧画面Formクラス
 */
@Data
public class UserListForm {
    
    /**ログインID*/
    private String loginId;
    
    /**アカウント状態種別*/
    private  UserStatusKind userStatusKind;
    
    /**ユーザーの権限種別*/
    private AuthorityKind authorityKind;
    
    /**ユーザー一覧情報から選択されたログインID*/
    private String selectedLoginId;
    
    /**
     * ユーザー一覧情報から選択されたログインIDをクリアします
     * 
     * @return ユーザー一覧情報から選択されたログインIDクリア後のインスタンス
     */
    
    public UserListForm clearSelectedLoginId() {
        this.selectedLoginId = null;
        return this;
    }

}
