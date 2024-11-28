package com.example.ankiapp.dto;

import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import lombok.Data;


/**
 * ユーザー更新情報DTOクラス
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
