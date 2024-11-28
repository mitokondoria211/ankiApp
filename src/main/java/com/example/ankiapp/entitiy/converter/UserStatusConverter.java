package com.example.ankiapp.entitiy.converter;

import com.example.ankiapp.constant.db.UserStatusKind;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * ユーザー情報 ユーザー状態種別フィールドConverterクラス
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatusKind, Boolean>{

    /**
     *引数で受け取ったユーザー状態種別Enumを、利用可否のBooleanに変換します
     *@param    ユーザー状態種別Enum
     *@return 引数で受け取ったユーザー状態種別Enumに保管されているboolean
     */
    @Override
    public Boolean convertToDatabaseColumn(UserStatusKind userStatus) {
        // TODO 自動生成されたメソッド・スタブ
        return userStatus.isDisabled();
    }
    
    /**
     *引数で受け取った権限種別のコード値を、ユーザー状態種別Enumに変換します
     *@param    利用可否(利用不可ならtrue)
     *@return 引数で受け取った利用可否から逆引きしたユーザー状態種別Enum
     */
    @Override
    public UserStatusKind convertToEntityAttribute(Boolean isDisabled) {
        // TODO 自動生成されたメソッド・スタブ
        return isDisabled ? UserStatusKind.DISABLED : UserStatusKind.ENABLED;
    }

}
