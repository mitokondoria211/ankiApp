package com.example.ankiapp.dto;

import lombok.Data;
import com.example.ankiapp.entitiy.DeckInfo;
/**
 * カード一覧検索用DTOクラス
 */
@Data
public class CardPracticeInfo {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    private String cardName;
    
    /**問題*/
    private String question;
    
    /**解答*/
    private String answer;
    
    /**問題画像url*/
    private String questionImageUrl;
    
    /**解答画像url*/
    private String answerImageUrl;
    
    /**デッキ情報*/
    private DeckInfo deckInfo;
    
}
