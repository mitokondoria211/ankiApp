package com.example.ankiapp.dto;

import lombok.Data;

/**
 * カードCSV画面DTOクラス
 */
@Data
public class CardCsvInfo {
    
    /**デッキID*/
    private Long deckId;

    /**カード名*/
    private String cardName;
    
    /**質問*/
    private String question;
    
    /**解答*/
    private String answer;
}
