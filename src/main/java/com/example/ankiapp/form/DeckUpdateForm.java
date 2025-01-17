package com.example.ankiapp.form;


import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

/**
 * デッキ更新画面Formクラス
 */
@Data
public class DeckUpdateForm {
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキタイトル*/
    @Length(min = 5, max = 30, message = "{title.deckUpdateForm.length}")
    private String title;
    
    /**デッキ説明*/
    @Length(max = 50, message = "{description.deckUpdateForm.length}")
    private String description;
        
    /**デッキ画像パス*/
    private String imagePath;
    
    /**デッキ画像ファイル*/
    private MultipartFile imageFile;
    
    
}
