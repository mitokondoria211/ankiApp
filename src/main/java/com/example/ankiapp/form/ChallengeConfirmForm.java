package com.example.ankiapp.form;

import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class ChallengeConfirmForm {
    
    private Integer size;
    
    private List<CardInfo> cards;
    
    private String cardResult;
    
}
