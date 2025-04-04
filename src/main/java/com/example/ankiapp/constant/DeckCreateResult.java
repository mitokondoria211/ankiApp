package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 *デッキ作成結果種別 
 */
@Getter
@AllArgsConstructor
public enum DeckCreateResult {
    
    /**エラーなし */
    SUCCEED(MessageConst.DECKEDIT_CREATE_SUCCEED),

    /** DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.DECKEDIT_DB_FAILED),
    
    /**画像サイズエラー*/
    FAILURE_BY_IMAGE_SIZE_ERROR(MessageConst.DECKEDIT_IMAGE_SIZE_FAILED),
    
    /**画像処理エラー*/
    FAILURE_BY_IMAGE_ERROR(MessageConst.DECKEDIT_IMAGE_FAILED);

    /**メッセージID*/
    private String messageId;
    
    @Override
    public String toString() {
        return messageId;
    }

}
