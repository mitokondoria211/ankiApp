package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


 /**
 * カード作成結果種別
 */
@Getter
@AllArgsConstructor
public enum CreateCardResult {
    
    /**エラーなし */
    SUCCEED(MessageConst.CARDINFO_CREATE_SUCCEED),

    /** DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.CARDINFO_DB_FAILED),
    
    /**画像サイズエラー*/
    FAILURE_BY_IMAGE_SIZE_ERROR(MessageConst.CARDINFO_IMAGE_SIZE_FAILED),
    
    /**画像処理エラー*/
    FAILURE_BY_IMAGE_ERROR(MessageConst.CARDINFO_IMAGE_FAILED);

    /**メッセージID*/
    private String messageId;
    
    @Override
    public String toString() {
        return messageId;
    }

}
