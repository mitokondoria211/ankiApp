package com.example.ankiapp.entitiy.converter;

import com.example.ankiapp.constant.db.CardAnswerResult;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * カード情報 カード評価種別フィールドConverterクラス
 */
@Converter
public class CardAnswerResultConverter implements AttributeConverter<CardAnswerResult, Integer>{

    /**
     *引数で受け取ったカード評価種別Enumを、カード評価種別コード値に変換します
     *@param    カード評価種別Enum
     *@return 引数で受け取ったカード評価種別Enumに保管されているコード値
     */
    @Override
    public Integer convertToDatabaseColumn(CardAnswerResult cardResult) {
        return cardResult.getCode();
    }

    /**
     *引数で受け取ったカード評価種別コード値を、カード評価種別Enumに変換します
     *@param カード評価種別コード値
     *@return 引数で受け取ったカード評価種別のコード値から逆引きしたカード評価種別Enum
     */
    @Override
    public CardAnswerResult convertToEntityAttribute(Integer cardCode) {
        return CardAnswerResult.from(cardCode);
    }

}
