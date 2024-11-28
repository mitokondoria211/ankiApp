package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreateCardResult {
    
    /**エラーなし */
    SUCCEED(MessageConst.SIGNUP_TENTATIVE_SUCCEED),

    /** DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.SIGNUP_DB_ERROR),

    /** メール送信失敗 */
    FAILURE_BY_MAIL_SEND_ERROR(MessageConst.SIGNUP_MAIL_SEND_ERROR);

    
    private String messageId;

}
