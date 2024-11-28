package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;
import com.example.ankiapp.constant.*;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.repository.DeckInfoRepository;
/**
 * ユーザー一覧検索用DTOクラス
 */
@Data
public class CardListInfo {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    private String cardName;
    
    /**カード名*/
    private String question;
    
    /**カード名*/
    private String answer;
    
    private CardAnswerResult cardResult;
    
    /**デッキソートするタイプ*/
    private SortType sortType;
    
    /**登録日時*/
    private LocalDateTime createdAt;

    /**更新日時*/
    private LocalDateTime updatedAt;
    
    private DeckInfo deckInfo;
    
}
