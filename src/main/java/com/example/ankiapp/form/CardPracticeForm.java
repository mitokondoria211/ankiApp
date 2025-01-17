package com.example.ankiapp.form;

import lombok.Data;

/**
 * カード練習画面 Form
 */
@Data
public class CardPracticeForm {
    
    /**カードID*/
    private Long cardId;
    
    /**デッキID*/
    private Long deckId;
    
}
