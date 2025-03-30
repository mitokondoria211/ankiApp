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
    
    /**エラー*/
    ERROR(MessageConst.DECKLIST_NON_EXISTED_DECK_ID),
    
    /**画像処理エラー*/
    IMAGE_ERROR(MessageConst.DECKLIST_DELETE_IMAGE_FAILED);
    
    /**メッセージID*/
    private String messageId;
}
