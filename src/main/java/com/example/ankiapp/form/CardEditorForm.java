package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.form.ValidGroups.ValidGroup1;
import com.example.ankiapp.form.ValidGroups.ValidGroup2;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ログイン画面 Form
 */
@Data
public class CardEditorForm {
    
    private Long deckId;
    
    @NotBlank
    @Length(min = 4, max = 25, message="{cardName.cardEditorForm.length}")
    private String cardName;
    @NotBlank
    @Length(min = 4, max = 1000, message="{question.cardEditorForm.length}")
    private String question;
    @NotBlank
    @Length(min = 1, max = 1000, message="{answer.cardEditorForm.length}")
    private String answer;
    
    /**画像*/
    private MultipartFile QuestionImageFile;
    /**画像*/
    private MultipartFile AnswerImageFile;
}
