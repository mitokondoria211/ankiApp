package com.example.ankiapp.form;


import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

/**
 * デッキ更新画面Formクラス
 */
@Data
public class DeckUpdateForm {
    
    /**デッキタイトル*/
    private Long deckId;
    
    /**デッキタイトル*/
    private String title;
    
    /**デッキ説明*/
    private String description;
    
    /**デッキ説明*/
    private MultipartFile imageFile;
}
