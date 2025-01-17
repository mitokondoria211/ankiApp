package com.example.ankiapp.form;

import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.DeckInfo;
import lombok.Data;

/**
 * カードリスト画面 Form
 */
@Data
public class CardListForm {
    
    /**選択されたカードID*/
    private Long selectedCardId;
    
    /**カード名*/
    private String cardName;
    
    /**問題*/
    private String question;
    
    /**解答*/
    private String answer;
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキ情報*/
    private DeckInfo deckInfo;
    
    /**カード評価*/
    private CardAnswerResult cardResult;
    
    /**ソートタイプ*/
    private SortType sortType;
    
    /**カードIDをクリアにするメソッド*/
    public CardListForm clearSelectedCardId() {
        this.selectedCardId = null;
        return this;
    }

}
