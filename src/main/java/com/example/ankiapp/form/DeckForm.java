package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * デッキ作成画面 Form
 */
@Data
public class DeckForm {
    
    /**デッキID*/
    private Long deckId;
    
    /**デッキタイトル*/
    @Length(min = 5, max = 30, message = "{title.deckForm.length}")
    private String title;
    
    /**デッキ説明*/
    @Length(max = 50, message = "{description.deckForm.length}")
    private String description;
    
    /**デッキ画像ファイル*/
    private MultipartFile imageFile;
}
