package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *処理結果種別 
 */
@Getter
@AllArgsConstructor
public enum CardDeleteResult {
    
    /**エラーなし*/
    SUCCEED(MessageConst.CARDLIST_DELETE_SUCCEED),
    
    /**エラーなし*/
    ERROR(MessageConst.CARDLIST_NON_EXISTED_CARD_ID);
    
    /**メッセージID*/
    private String messageId;
}
