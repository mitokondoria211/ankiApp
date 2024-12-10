package com.example.ankiapp.form;

import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class CardDisplayForm {
    
    private Long cardId;
    
    private Long deckId;
    
    private List<CardInfo> cards;
    
    private String imageFile;
}
