package com.example.ankiapp.constant.db;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザー権限種別
 */
@Getter
//@NoArgsConstructor
@AllArgsConstructor
public enum AuthorityKind {

    /** 登録内容が不正 */
    UNKNOWN("", "登録内容が不正"),

    /** アプリの使用が可能 */
    ANKI_MANEGER("user1", "アプリの使用が可能"),

    /** アプリの使用、全ユーザー情報の管理が可能 */
    ANKI_AND_USER_MANAGER("user2", "アプリの使用が可能、全ユーザー情報の管理が可能");

    /**コード値*/
    private String code;
    
    /**画面表示する説明文*/
    private String displayValue;

    /**
     * Enum 逆引き
     * 
     * 権限種別からEnumを逆引きします
     * 
     * @param code  権限種別コード順
     * @return 引数の権限種別コード値に対応するEnum、ただし見つからなかった場合はUNKNOWN
     */
    public static AuthorityKind from(String code) {
        return Arrays.stream(AuthorityKind.values())
            .filter(AuthorityKind -> AuthorityKind.getCode().equals(code))
            .findFirst()
            .orElse(UNKNOWN);
    }
}
