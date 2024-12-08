package com.example.ankiapp.form;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class CardEditorForm {
    
    private Long deckId;
    
    @NotBlank
    private String cardName;
    @NotBlank
    private String question;
    @NotBlank
    private String answer;
    
    /**画像*/
    private MultipartFile QuestionImageFile;
    /**画像*/
    private MultipartFile AnswerImageFile;
}
