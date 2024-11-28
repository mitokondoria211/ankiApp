package com.example.ankiapp.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * デッキ一覧画面DTOクラス
 */
@Data
public class DeckListInfo {
    
    /**デッキid*/
    private Long deckId;
    
    /**デッキタイトル*/
    private String title;
    
    /**デッキ説明*/
    private String description;
    
    /**登録日時*/
    private LocalDateTime createdAt;

    /**更新日時*/
    private LocalDateTime updatedAt;
    

}
