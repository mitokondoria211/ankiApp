package com.example.ankiapp.form;

import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.repository.DeckInfoRepository;
import lombok.Data;

@Data
public class CardListForm {
    
    private Long selectedCardId;
    
    private String cardName;
    
    private String question;
    
    private String answer;
    
    private Long deckId;
    
    private DeckInfo deckInfo;
    
    private CardAnswerResult cardResult;
    
    private SortType sortType;
    
    public CardListForm clearSelectedCardId() {
        this.selectedCardId = null;
        return this;
    }

}
