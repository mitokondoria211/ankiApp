package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpdateCardResult {
    
    /**エラーなし */
    SUCCEED(MessageConst.UPDATECARD_UPDATE_SUCCEED),

    /** DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.UPDATECARD_DB_FAILED),
    
    /**画像サイズエラー*/
    FAILURE_BY_IMAGE_SIZE_ERROR(MessageConst.UPDATECARD_IMAGE_SIZE_FAILED),
    
    /**画像処理エラー*/
    FAILURE_BY_IMAGE_ERROR(MessageConst.UPDATECARD_IMAGE_FAILED);

    
    private String messageId;
    
    @Override
    public String toString() {
        return messageId;
    }

}
