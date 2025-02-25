package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


 /**
 * カードCSV取り込み結果種別
 */
@Getter
@AllArgsConstructor
public enum CardCsvImportResult {
    
    /**成功*/
    SUCCEED(MessageConst.CARDFROMCSV_IMPORT_SUCCEED),
    
    /**エラーなし*/
    ERROR(MessageConst.CARDFROMCSV_IMPORT_FAILED);
    
    /**メッセージID*/
    private String messageId;
}
