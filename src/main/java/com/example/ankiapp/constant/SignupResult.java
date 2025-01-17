package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 *登録結果種別
 */
@Getter
@AllArgsConstructor
public enum SignupResult {
    
    /** エラーなし */
    SUCCEED(MessageConst.SIGNUP_TENTATIVE_SUCCEED),

    /**既に本登録済み */
    FAILURE_BY_ALREADY_COMPLETED(MessageConst.SIGNUP_ALREADY_COMPLETED),

    /** 仮登録状態 */
    FAILURE_BY_SIGNUP_PROCEEDING(MessageConst.SIGNUP_SIGNUP_PROCEEDING),

    /**DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.SIGNUP_DB_ERROR),

    /** メール送信失敗 */
    FAILURE_BY_MAIL_SEND_ERROR(MessageConst.SIGNUP_MAIL_SEND_ERROR);

    /** メッセージID */
    private String messageId;

}
