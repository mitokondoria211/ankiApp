package com.example.ankiapp.constant.db;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardAnswerResult {
    
    UNRATED(0, "未評価"),
    AGAIN(1,"もう一度"),
    DIFFICULT(2,"難しい"),
    INCORRECT(3,"不正解"),
    CORRECT(4,"正解"),
    EASY(5, "簡単");
    
    private  Integer code;
    private  String rating;
    
    public static CardAnswerResult from(Integer code) {
        return Arrays.stream(CardAnswerResult.values())
                .filter(result -> result.getCode().equals(code))
                .findFirst()
                .orElse(UNRATED);
    }
    
//    private static CardAnswerResult [] results = {
//        UNRATED,
//        AGAIN,
//        DIFFICULT,
//        INCORRECT,
//        CORRECT,
//        EASY
//    };
}
