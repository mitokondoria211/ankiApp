package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


 /**
 * カード削除結果種別
 */
@Getter
@AllArgsConstructor
public enum CardCsvImportResult {
    
    /**エラーなし*/
    SUCCEED(MessageConst.CARDFROMCSV_IMPORT_SUCCEED),
    
    /**エラーなし*/
    ERROR(MessageConst.CARDFROMCSV_IMPORT_FAILED);
    
    /**メッセージID*/
    private String messageId;
}
