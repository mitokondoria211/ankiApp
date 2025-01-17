package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;
import com.example.ankiapp.constant.*;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.DeckInfo;
/**
 * カード一覧検索用DTOクラス
 */
@Data
public class CardSearchInfo {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    private String cardName;
    
    /**問題*/
    private String question;
    
    /**解答*/
    private String answer;
    
    /**カード評価*/
    private CardAnswerResult cardResult;
    
    /**デッキソートするタイプ*/
    private SortType sortType;
    
    /**登録日時*/
    private LocalDateTime createdAt;

    /**更新日時*/
    private LocalDateTime updatedAt;
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキ情報*/
    private DeckInfo deckInfo;
    
}
