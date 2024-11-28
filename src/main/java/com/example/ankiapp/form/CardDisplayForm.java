package com.example.ankiapp.form;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class CardDisplayForm {
    
    private Long cardId;
    
    private Long deckId;
    
    private String imageFile;
}
