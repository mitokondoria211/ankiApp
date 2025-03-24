package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 *デッキ更新結果種別
 */
@Getter
@AllArgsConstructor
public enum DeckUpdateResult {
    
    /**エラーなし */
    SUCCEED(MessageConst.UPDATEDECK_UPDATE_SUCCEED),

    /** DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.UPDATEDECK_DB_FAILED),
    
    /**画像サイズエラー*/
    FAILURE_BY_IMAGE_SIZE_ERROR(MessageConst.UPDATEDECK_IMAGE_SIZE_FAILED),
    
    /**画像処理エラー*/
    FAILURE_BY_IMAGE_ERROR(MessageConst.UPDATEDECK_IMAGE_FAILED);

    /**メッセージID*/
    private String messageId;
    
    @Override
    public String toString() {
        return messageId;
    }

}
