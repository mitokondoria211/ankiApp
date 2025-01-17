package com.example.ankiapp.dto;

import lombok.Data;


/**
 * デッキ更新情報DTOクラス
 */
@Data
public class DeckUpdateInfo {
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキタイトル*/
    private String title;
    
    /**デッキ説明*/
    private String description;
}
