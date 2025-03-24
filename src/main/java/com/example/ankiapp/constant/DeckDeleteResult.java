package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


 /**
 * デッキ削除結果種別
 */
@Getter
@AllArgsConstructor
public enum DeckDeleteResult {
    
    /**成功*/
    SUCCEED(MessageConst.DECKLIST_DELETE_SUCCEED),
    
    /**エラーなし*/
    ERROR(MessageConst.DECKLIST_NON_EXISTED_DECK_ID);
    
    /**メッセージID*/
    private String messageId;
}
