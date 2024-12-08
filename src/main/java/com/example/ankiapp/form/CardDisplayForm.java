package com.example.ankiapp.form;

import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class CardDisplayForm {
    
    private Long cardId;
    
    private Long deckId;
    
    private String imageFile;
}
