package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;
import com.example.ankiapp.constant.*;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.DeckInfo;
/**
 * ユーザー一覧検索用DTOクラス
 */
@Data
public class CardSearchInfo {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    private String cardName;
    
    /**カード名*/
    private String question;
    
    /**カード名*/
    private String answer;
    
    /**カード評価*/
    private CardAnswerResult cardResult;
    
    /**デッキソートするタイプ*/
    private SortType sortType;
    
    /**登録日時*/
    private LocalDateTime createdAt;

    /**更新日時*/
    private LocalDateTime updatedAt;
    
    private Long deckId;
    
    private DeckInfo deckInfo;
    
}
