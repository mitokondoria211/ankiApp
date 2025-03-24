package com.example.ankiapp.form;

import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;

/**
 * カード練習画面 Form
 */
@Data
public class CardDisplayForm {
    
    /**カードID*/
    private Long cardId;
    
    /**デッキID*/
    private Long deckId;
    
    /**カードリスト*/
    private List<CardInfo> cards;
    
    /**画像パス*/
    private String imageFile;
}
