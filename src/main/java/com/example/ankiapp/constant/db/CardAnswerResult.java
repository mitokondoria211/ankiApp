package com.example.ankiapp.constant.db;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;



/**
 * カード回答評価種別
 */
@Getter
@AllArgsConstructor
public enum CardAnswerResult {
    
    /**未評価*/
    UNRATED(0, "未評価"),
    
    /**もう一度*/
    AGAIN(1,"もう一度"),
    
    /**難しい*/
    DIFFICULT(2,"難しい"),
    
    /**不正解*/
    INCORRECT(3,"不正解"),
    
    /**正解*/
    CORRECT(4,"正解"),
    
    /**簡単*/
    EASY(5, "簡単");
    
    /**コード*/
    private  Integer code;
    
    /**評価名*/
    private  String rating;
    
    
    /**
     * コードからカード回答評価に変換します。
     * ただし、コードが存在しない場合は未評価になります。
     * 
     * @param code 評価コード
     * @return カード解答評価種別
     */
    public static CardAnswerResult from(Integer code) {
        return Arrays.stream(CardAnswerResult.values())
                .filter(result -> result.getCode().equals(code))
                .findFirst()
                .orElse(UNRATED);
    }
    
}
