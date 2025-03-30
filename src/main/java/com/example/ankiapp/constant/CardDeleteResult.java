package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


 /**
 * カード削除結果種別
 */
@Getter
@AllArgsConstructor
public enum CardDeleteResult {
    
    /**エラーなし*/
    SUCCEED(MessageConst.CARDLIST_DELETE_SUCCEED),
    
    /**エラーなし*/
    ERROR(MessageConst.CARDLIST_NON_EXISTED_CARD_ID),
    
    /**エラーなし*/
    IMAGE_DELETE_ERROR(MessageConst.CARDLIST_IMAGE_DELETE_FAILED);

    /**メッセージID*/
    private String messageId;
}
