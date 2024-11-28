package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;
import com.example.ankiapp.constant.*;
/**
 * ユーザー一覧検索用DTOクラス
 */
@Data
public class DeckSearchInfo {
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキタイトル*/
    private String title;
    
    /**デッキソートするタイプ*/
    private SortType sortType;
    
    /**デッキ説明*/
    private String description;
    
    /**登録日時*/
    private LocalDateTime createdAt;

    /**更新日時*/
    private LocalDateTime updatedAt;
    
}
