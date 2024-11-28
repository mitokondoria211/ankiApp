package com.example.ankiapp.entitiy.converter;

import com.example.ankiapp.constant.db.CardAnswerResult;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * ユーザー情報 権限フィールドConverterクラス
 */
@Converter
public class CardAnswerResultConverter implements AttributeConverter<CardAnswerResult, Integer>{

    @Override
    public Integer convertToDatabaseColumn(CardAnswerResult cardResult) {
        // TODO 自動生成されたメソッド・スタブ
        return cardResult.getCode();
    }

    @Override
    public CardAnswerResult convertToEntityAttribute(Integer cardCode) {
        // TODO 自動生成されたメソッド・スタブ
        return CardAnswerResult.from(cardCode);
    }

    /**
     *引数で受け取った権限種別Enumを、権限種別コード値に変換します
     *@param    権限種別Enum
     *@return 引数で受け取った権限種別Enumに保管されているコード値
     */
//    @Override
//    public String convertToDatabaseColumn(AuthorityKind authorityKind) {
//        // TODO 自動生成されたメソッド・スタブ
//        return authorityKind.getCode();
//    }
    
    /**
     *引数で受け取った権限種別のコード値を、権限種別Enumに変換します
     *@param 権限種別のコード値
     *@return 引数で受け取った権限種別のコード値から逆引きした権限種別Enum
     */
//    @Override
//    public AuthorityKind convertToEntityAttribute(String value) {
//        // TODO 自動生成されたメソッド・スタブ
//        return AuthorityKind.from(value);
//    }

}
