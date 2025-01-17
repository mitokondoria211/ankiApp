package com.example.ankiapp.form;

import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;

/**
 * チャレンジ確認画面 Form
 */
@Data
public class ChallengeConfirmForm {
    
    /**問題数*/
    private Integer size;
    
    /**deck内のカード*/
    private List<CardInfo> cards;
    
    /**カード評価*/
    private String cardResult;
    
}
