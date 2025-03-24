package com.example.ankiapp.dto;

import lombok.Data;

/**
 * デッキ画面情報DTOクラス
 */
@Data
public class DeckDisplayInfo {

    /**デッキID*/
    private Long deckId;
    
    /**デッキ画像*/
    private String deckImage;
}
